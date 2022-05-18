import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
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
            buildConfigField("String", "NEWS_API_KEY", apiKey)
            buildConfigField("String", "NEWS_URL", "\"https://newsapi.org/v2/\"")
        }
    }
}

dependencies {
    implementation(project(":core"))

    withDrawerImplementation(Libs.DebugDrawer.retrofit)
    withDrawerImplementation(Libs.DebugDrawer.okhttp)
    withDrawerImplementation(Libs.DebugDrawer.main)

    implementation(Libs.Retrofit.client)
    implementation(Libs.Retrofit.moshiConverter)

    implementation(Libs.OkHttp.client)
    devImplementation(Libs.OkHttp.loggingInterceptor)

    implementation(Libs.Moshi.kotlin)
    implementation(Libs.Moshi.adapters)
    ksp(Libs.Moshi.compiler)
}
