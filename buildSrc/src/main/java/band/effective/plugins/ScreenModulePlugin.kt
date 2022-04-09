package band.effective.plugins

import Libs
import Versions
import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class ScreenModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project.plugins) {
            apply("band.effective.app")
            apply("com.google.devtools.ksp")
        }
        val androidExtension = project.extensions.getByName("android")
        if (androidExtension is BaseExtension) {
            with(androidExtension) {
                applyCompose(project)
                applyNavigation(project)
            }
        }
    }

    private fun BaseExtension.applyCompose(project: Project) {
        buildFeatures.compose = true
        composeOptions.kotlinCompilerExtensionVersion = Versions.AndroidX.Compose.version


        project.dependencies {
            add("implementation", Libs.AndroidX.coreKtx)

            add("implementation", Libs.AndroidX.Lifecycle.runtime)

            add("implementation", Libs.AndroidX.Activity.activityCompose)

            add("implementation", Libs.AndroidX.Compose.ui)
            add("implementation", Libs.AndroidX.Compose.material)
            add("implementation", Libs.AndroidX.Compose.material3)
            add("implementation", Libs.AndroidX.Compose.materialIconsExtended)
            add("debugImplementation", Libs.AndroidX.Compose.tooling)
            add("debugImplementation", Libs.AndroidX.Compose.toolingPreview)

            add("androidTestImplementation", Libs.AndroidX.Test.composeUiJunit)
        }
    }

    private fun BaseExtension.applyNavigation(project: Project) {
        val kotlin = project.extensions.getByName("kotlin") as KotlinAndroidProjectExtension
        when (this) {
            is LibraryExtension -> libraryVariants.all {
                kotlin.sourceSets.getByName(name).kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
            is AppExtension -> applicationVariants.all {
                kotlin.sourceSets.getByName(name).kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }

        project.dependencies {
            add("implementation", Libs.Navigation.destinations)
            add("ksp", Libs.Navigation.compiler)
        }
    }
}
