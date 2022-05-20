plugins {
    alias(libs.plugins.android.library)
    id("band.effective.module.compose")
}

dependencies {
    implementation(project(":core"))

    implementation(libs.androidx.paging.compose)
}
