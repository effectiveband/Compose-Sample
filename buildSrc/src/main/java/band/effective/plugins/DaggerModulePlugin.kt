package band.effective.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.BaseExtension
import org.gradle.kotlin.dsl.dependencies

class DaggerModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project.plugins) {
            apply("band.effective.build.config")
            apply("org.jetbrains.kotlin.kapt")
        }
        val androidExtension = project.extensions.getByName("android")
        if (androidExtension is BaseExtension) {
            applyDagger(project)
        }
    }

    private fun applyDagger(project: Project) {
        project.dependencies {
            add("implementation", Libs.Dagger2.annotations)
            add("kapt", Libs.Dagger2.compiler)
        }
    }
}
