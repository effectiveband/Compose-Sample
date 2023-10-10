plugins {
    alias(libs.plugins.android.library)
    id("band.effective.module.dagger")
    id("com.google.devtools.ksp")
}

android {
    namespace = "band.effective.headlines.compose.news_api"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":network"))

    implementation(libs.androidx.paging)

    implementation(libs.retrofit.client)

    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.compiler)
}
