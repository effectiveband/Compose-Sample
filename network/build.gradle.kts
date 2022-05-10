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
            // Duplication only for example
            buildConfigField("String", "NEWS_STAGE_API_KEY", apiKey)
            buildConfigField("String", "NEWS_API_KEY", apiKey)
        }
        getByName("prod") {
            buildConfigField("String", "NEWS_API_KEY", apiKey)
        }
    }
}

dependencies {
    implementation(project(":core"))

    implementation(Libs.DebugDrawer.retrofit)
    implementation(Libs.DebugDrawer.okhttp)
    implementation(Libs.DebugDrawer.main)

    implementation(Libs.Retrofit.client)
    implementation(Libs.Retrofit.moshiConverter)

    implementation(Libs.OkHttp.client)
    implementation(Libs.OkHttp.loggingInterceptor)

    implementation(Libs.Moshi.kotlin)
    implementation(Libs.Moshi.adapters)
    ksp(Libs.Moshi.compiler)
}
