package band.effective.plugins

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class NetworkConfigPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        val androidExtension = project.extensions.getByName("android")
        if (androidExtension is BaseExtension) {
            androidExtension.applyApiKey(getApiKey(project))
        }
    }

    private fun getApiKey(project: Project): String {
        return gradleLocalProperties(project.rootDir).getProperty("apiKey")
    }

    private fun BaseExtension.applyApiKey(apiKey: String) {
        productFlavors {
            getByName("dev") {
                buildConfigField("String", "NEWS_BASE_URL", "\"https://newsapi.org/v2/\"")
                buildConfigField("String", "NEWS_API_KEY", apiKey)
            }
            getByName("prod") {
                buildConfigField("String", "NEWS_BASE_URL", "\"https://newsapi.org/v2/\"")
                buildConfigField("String", "NEWS_API_KEY", apiKey)
            }
        }
    }
}
