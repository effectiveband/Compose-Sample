plugins {
    alias(libs.plugins.android.library)
    id("band.effective.module.dagger")
}

android {
    namespace = "band.effective.headlines.compose.drawer"
}

dependencies {

    implementation(project(":core"))
    implementation(project(":network"))

    implementation(libs.retrofit.client)
    withDrawerImplementation(libs.retrofit.mock)

    withDrawerImplementation(libs.debug.drawer)
    withDrawerImplementation(libs.debug.drawer.retrofit)
    withDrawerImplementation(libs.debug.drawer.okhttp)
    withDrawerImplementation(libs.debug.drawer.timber)
}
