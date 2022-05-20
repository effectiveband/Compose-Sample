plugins {
    alias(libs.plugins.android.library)
    id("band.effective.module.dagger")
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
}
