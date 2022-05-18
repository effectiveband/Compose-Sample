package band.effective.plugins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

class BuildConfigPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project.plugins) {
            apply("org.jetbrains.kotlin.android")
            apply("kotlin-parcelize")
        }
        val androidExtension = project.extensions.getByName("android")
        if (androidExtension is BaseExtension) {
            with(androidExtension) {
                applyAndroidSettings()
                applyBuildTypes()
                applySigningConfig(project)
                applyProguardSettings(project)
                applyJava8(project)
                applyBaseDependencies(project)
            }
        }
    }

    private fun BaseExtension.applyAndroidSettings() {
        compileSdkVersion(Configs.compileSdk)
        defaultConfig {
            minSdk = Configs.minSdk
            targetSdk = Configs.targetSdk
        }
    }

    private fun BaseExtension.applyBuildTypes() {
        flavorDimensions("default", "drawer")
        productFlavors {
            create("dev") {
                dimension = "default"
                if (this@applyBuildTypes is AppExtension) {
                    versionNameSuffix = "-dev"
                    applicationIdSuffix = ".dev"
                }
            }
            create("prod") {
                dimension = "default"
            }
            create("withDrawer") {
                dimension = "drawer"
            }
            create("noDrawer") {
                dimension = "drawer"
            }
        }
        variantFilter {
            val isNotDevDebugBuild = !name.contains("dev") && name.contains("Debug")
            val isPublicBuildDDrawer = name.contains("prod") && !name.contains("NoDrawer")
            val isDevBuildNoDDrawer = name.contains("dev") && name.contains("NoDrawer")
            if (isNotDevDebugBuild || isPublicBuildDDrawer || isDevBuildNoDDrawer) {
                ignore = true
            }
        }
    }

    private fun BaseExtension.applySigningConfig(project: Project) {
        signingConfigs {
            create("internal") {
                storeFile = File("${project.rootDir}/internalKeystore/headlineskey")
                storePassword = "effectivepswd"
                keyAlias = "effectivekey"
                keyPassword = "effectivepswd"
            }
        }
    }

    private fun BaseExtension.applyProguardSettings(project: Project) {
        val proguardFolder = "${project.projectDir}/proguard"
        val coroutines = "$proguardFolder/coroutines.pro"
        val okhttp = "$proguardFolder/okhttp3.pro"
        val okio = "$proguardFolder/okio.pro"
        val retrofit2 = "$proguardFolder/retrofit2.pro"
        when (this) {
            is LibraryExtension -> defaultConfig {
                consumerProguardFiles(
                    coroutines,
                    okhttp,
                    okio,
                    retrofit2
                )
            }
            is AppExtension -> buildTypes {
                getByName("release") {
                    isMinifyEnabled = true
                    signingConfig = signingConfigs.getByName("internal")
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        coroutines,
                        okhttp,
                        okio,
                        retrofit2
                    )
                }
            }
        }
    }

    private fun BaseExtension.applyJava8(project: Project) {
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        project.tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-Xopt-in=kotlin.RequiresOptIn",
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-opt-in=kotlinx.coroutines.ObsoleteCoroutinesApi"
                )
                jvmTarget = "1.8"
            }
        }
    }

    private fun applyBaseDependencies(project: Project) {
        project.dependencies {
            add("implementation", Libs.Coroutines.android)
            add("implementation", Libs.Timber.timber)
        }
    }
}
