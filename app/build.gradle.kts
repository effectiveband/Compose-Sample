plugins {
    id("com.android.application")
    id("band.effective.module.screen")
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

    implementation(project(":screens:feed"))

    testImplementation(Libs.AndroidX.Test.junit)
    androidTestImplementation(Libs.AndroidX.Test.junitExt)
    androidTestImplementation(Libs.AndroidX.Test.espressoCore)
}
