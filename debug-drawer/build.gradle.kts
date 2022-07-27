plugins {
    alias(libs.plugins.android.library)
    id("band.effective.module.dagger")
}

dependencies {

    implementation(project(":core"))
    implementation(project(":network"))

    implementation(libs.retrofit.client)
    implementation(libs.retrofit.mock)

    implementation(libs.drawer.base)
    implementation(libs.drawer.modules)
    implementation(libs.drawer.location)
}
