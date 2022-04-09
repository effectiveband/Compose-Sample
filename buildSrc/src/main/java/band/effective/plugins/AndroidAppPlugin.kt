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

class AndroidAppPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.apply("org.jetbrains.kotlin.android")
        val androidExtension = project.extensions.getByName("android")
        if(androidExtension is BaseExtension) {
            with(androidExtension) {
                applyAndroidSettings()
                applyJava8(project)
                applyBuildTypes()
                applyProguardSettings()
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
            if (isNotDevDebugBuild || isPublicBuildDDrawer) {
                ignore = true
            }
        }
    }

    private fun BaseExtension.applyProguardSettings() {
        val proguardFile = "proguard-rules.pro"
        when(this) {
            is LibraryExtension -> defaultConfig {
                consumerProguardFiles(proguardFile)
            }
            is AppExtension -> buildTypes {
                getByName("release") {
                    isMinifyEnabled = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        proguardFile
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
            kotlinOptions{
                jvmTarget = "1.8"
            }
        }
    }

    private fun applyBaseDependencies(project: Project) {
        project.dependencies {
            add("implementation", Libs.Coroutines.android)
        }
    }
}
