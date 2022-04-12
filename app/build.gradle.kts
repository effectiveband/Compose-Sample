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
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation(project(":network"))

    implementation(project(":screens:feed"))
    implementation(project(":screens:search"))
    implementation(project(":screens:about"))
    implementation(project(":screens:news-details"))

    implementation(Libs.Retrofit.client)
    implementation(Libs.Retrofit.moshiConverter)

    implementation(Libs.OkHttp.client)
    implementation(Libs.OkHttp.loggingInterceptor)

    implementation(Libs.Moshi.kotlin)
    implementation(Libs.Moshi.adapters)
    ksp(Libs.Moshi.compiler)

    testImplementation(Libs.AndroidX.Test.junit)
    androidTestImplementation(Libs.AndroidX.Test.junitExt)
    androidTestImplementation(Libs.AndroidX.Test.espressoCore)
}
