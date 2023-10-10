plugins {
    alias(libs.plugins.android.library)
    id("band.effective.module.dagger")
}

android {
    namespace = "band.effective.headlines.compose.core"
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
}
