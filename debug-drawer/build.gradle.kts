plugins {
    id("com.android.library")
    id("band.effective.module.dagger")
}

android {
    defaultConfig {
        buildConfigField("String", "NEWS_STAGE_URL", "\"https://newsapi.org/v2/\"")
        buildConfigField("String", "NEWS_URL", "\"https://newsapi.org/v2/\"")
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":network"))

    implementation(Libs.Retrofit.client)
    implementation(Libs.Retrofit.mock)

    implementation(Libs.DebugDrawer.main)
    implementation(Libs.DebugDrawer.retrofit)
    implementation(Libs.DebugDrawer.okhttp)
    implementation(Libs.DebugDrawer.timber)
}
