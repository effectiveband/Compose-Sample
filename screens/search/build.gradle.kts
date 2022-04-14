plugins {
    id("com.android.library")
    id("band.effective.module.screen")
}

dependencies {
    implementation(project(":news-api"))

    implementation(Libs.AndroidX.Paging.compose)
}
