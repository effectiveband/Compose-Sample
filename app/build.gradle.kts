plugins {
    alias(libs.plugins.android.application)
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

    implementation(libs.retrofit.client)
    implementation(libs.retrofit.moshi)
    implementation(libs.retrofit.mock)

    implementation(libs.okhttp.client)
    devImplementation(libs.okhttp.logginginterceptor)

    implementation(libs.drawer.base)
    implementation(libs.drawer.modules)
    implementation(libs.drawer.location)

    implementation(libs.androidx.splashscreen)
}
