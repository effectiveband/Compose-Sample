plugins {
    alias(libs.plugins.android.library)
    id("band.effective.module.screen")
}

dependencies {
    implementation(project(":news-api"))

    implementation(libs.androidx.paging.compose)
}
