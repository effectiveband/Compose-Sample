package band.effective.plugins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class ComposeModulePlugin: Plugin<Project> {

    override fun apply(project: Project) {
        with(project.plugins) {
            apply("band.effective.build.config")
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
            add("implementation", Libs.AndroidX.Lifecycle.viewModel)
            add("implementation", Libs.AndroidX.Lifecycle.viewModelCompose)

            add("implementation", Libs.AndroidX.Activity.activityCompose)

            add("implementation", Libs.AndroidX.Compose.ui)
            add("implementation", Libs.AndroidX.Compose.material)
            add("implementation", Libs.AndroidX.Compose.material3)
            add("implementation", Libs.AndroidX.Compose.materialIconsExtended)
            add("implementation", Libs.AndroidX.Compose.toolingPreview)
            add("debugImplementation", Libs.AndroidX.Compose.tooling)
            add("debugImplementation", Libs.AndroidX.Compose.composeUiManifest)

            add("implementation", Libs.Accompanist.insets)
            add("implementation", Libs.Accompanist.insetsUi)
            add("implementation", Libs.Accompanist.systemUiController)
            add("implementation", Libs.Accompanist.swipeRefreshLayout)

            add("androidTestImplementation", Libs.AndroidX.Test.composeUiJunit)
        }
    }

    private fun BaseExtension.applyNavigation(project: Project) {
        val kotlin = project.extensions.getByName("kotlin") as KotlinAndroidProjectExtension
        val ksp = project.extensions.getByName("ksp") as com.google.devtools.ksp.gradle.KspExtension
        when (this) {
            is LibraryExtension -> libraryVariants.all {
                kotlin.sourceSets.getByName(name).kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
            is AppExtension -> applicationVariants.all {
                kotlin.sourceSets.getByName(name).kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }

        ksp.apply {
            arg("compose-destinations.generateNavGraphs", "false")
        }

        project.dependencies {
            add("implementation", Libs.Navigation.destinations)
            add("ksp", Libs.Navigation.compiler)
        }
    }
}
