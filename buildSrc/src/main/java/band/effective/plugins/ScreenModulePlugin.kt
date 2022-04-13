package band.effective.plugins

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class ScreenModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project.plugins) {
            apply("band.effective.build.config")
            apply("band.effective.module.dagger")
            apply("band.effective.module.compose")
        }
        val androidExtension = project.extensions.getByName("android")
        if (androidExtension is BaseExtension) {
            project.dependencies {
                add("implementation", project(":core"))
                add("implementation", project(":core-ui"))

                add("implementation", Libs.Coil.coil)
            }
        }
    }
}
