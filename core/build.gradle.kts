plugins {
    id("com.android.library")
    id("band.effective.module.dagger")
}

dependencies {
    implementation(Libs.AndroidX.Lifecycle.viewModel)
    implementation(Libs.AndroidX.Lifecycle.viewModelSavedState)
}
