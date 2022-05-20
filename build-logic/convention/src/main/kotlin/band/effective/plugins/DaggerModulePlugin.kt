package band.effective.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.BaseExtension
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class DaggerModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project.plugins) {
            apply(BuildConfigPlugin::class.java)
            apply("kotlin-kapt")
        }
        val androidExtension = project.extensions.getByName("android")
        if (androidExtension is BaseExtension) {
            applyDagger(project)
        }
    }

    private fun applyDagger(project: Project) {
        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")
        project.dependencies {
            add("implementation", libs.findLibrary("dagger2-annotations").get())
            add("kapt", libs.findLibrary("dagger2-compiler").get())
        }
    }
}
