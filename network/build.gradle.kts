import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.library)
    id("band.effective.module.dagger")
    id("com.google.devtools.ksp")
}

android {
    val apiKey = gradleLocalProperties(project.rootDir).getProperty("apiKey")

    productFlavors {
        getByName("dev") {
            // Key duplication only for example
            buildConfigField("String", "NEWS_STAGE_API_KEY", apiKey)
            buildConfigField("String", "NEWS_API_KEY", apiKey)
            buildConfigField("String", "NEWS_STAGE_URL", "\"https://newsapi.org/v2/\"")
            buildConfigField("String", "NEWS_URL", "\"https://newsapi.org/v2/\"")
        }
        getByName("prod") {
            buildConfigField("String", "NEWS_STAGE_URL", "\"https://newsapi.org/v2/\"")
            buildConfigField("String", "NEWS_API_KEY", apiKey)
            buildConfigField("String", "NEWS_URL", "\"https://newsapi.org/v2/\"")
        }
    }
}

dependencies {
    implementation(project(":core"))

    withDrawerImplementation(libs.drawer.base)
    withDrawerImplementation(libs.drawer.modules)
    withDrawerImplementation(libs.drawer.location)

    implementation(libs.retrofit.client)
    implementation(libs.retrofit.moshi)

    implementation(libs.okhttp.client)
    devImplementation(libs.okhttp.logginginterceptor)

    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
    ksp(libs.moshi.compiler)
}
