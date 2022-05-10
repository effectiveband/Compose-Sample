plugins {
    id("com.android.application")
    id("band.effective.module.screen")
    id("band.effective.module.dagger")
}

android {
    defaultConfig {
        applicationId = "band.effective.headlines.compose"
        versionCode = 1
        versionName = "1.0"

        vectorDrawables.useSupportLibrary = true
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation(project(":debug-drawer"))
    implementation(project(":network"))
    implementation(project(":news-api"))

    implementation(project(":screens:feed"))
    implementation(project(":screens:search"))
    implementation(project(":screens:about"))
    implementation(project(":screens:article-details"))

    implementation(Libs.Retrofit.client)
    implementation(Libs.Retrofit.moshiConverter)
    implementation(Libs.Retrofit.mock)

    implementation(Libs.OkHttp.client)
    implementation(Libs.OkHttp.loggingInterceptor)

    implementation(Libs.DebugDrawer.main)
    implementation(Libs.DebugDrawer.retrofit)
    implementation(Libs.DebugDrawer.okhttp)
    implementation(Libs.DebugDrawer.timber)

    implementation(Libs.Moshi.kotlin)
    implementation(Libs.Moshi.adapters)
    ksp(Libs.Moshi.compiler)

    implementation(Libs.AndroidX.splashscreen)
}
