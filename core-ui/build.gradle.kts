plugins {
    id("com.android.library")
    id("band.effective.module.compose")
}

dependencies {
    implementation(project(":core"))

    implementation(Libs.AndroidX.Paging.compose)
}
