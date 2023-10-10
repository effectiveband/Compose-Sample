plugins {
    alias(libs.plugins.android.library)
    id("band.effective.module.screen")
}

android {
    namespace = "band.effective.headlines.compose.search"
}

dependencies {
    implementation(project(":news-api"))

    implementation(libs.androidx.paging.compose)
}
