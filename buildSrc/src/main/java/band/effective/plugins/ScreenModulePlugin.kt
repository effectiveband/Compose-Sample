package band.effective.plugins

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ScreenModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.apply("org.jetbrains.kotlin.android")
        project.plugins.apply("band.effective.app")
        val androidExtension = project.extensions.getByName("android")
        if (androidExtension is BaseExtension) {
            with(androidExtension) {
                applyCompose(project)
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
            add("implementation", Libs.AndroidX.Compose.material3)
            add("implementation", Libs.AndroidX.Compose.materialIconsExtended)
            add("debugImplementation", Libs.AndroidX.Compose.tooling)
            add("debugImplementation", Libs.AndroidX.Compose.toolingPreview)

            add("androidTestImplementation", Libs.AndroidX.Test.composeUiJunit)
        }
    }
}
