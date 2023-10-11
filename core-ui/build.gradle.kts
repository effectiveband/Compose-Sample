plugins {
    alias(libs.plugins.android.library)
    id("band.effective.module.compose")
}

android {
    namespace = "band.effective.headlines.compose.core_ui"
}

dependencies {
    implementation(project(":core"))

    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.navigation.common)
}
