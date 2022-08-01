plugins {
    alias(libs.plugins.android.library)
    id("band.effective.module.dagger")
}

dependencies {

    implementation(project(":core"))
    implementation(project(":network"))

    implementation(libs.retrofit.client)
    implementation(libs.retrofit.mock)

    withDrawerImplementation(libs.drawer.base)
    withDrawerImplementation(libs.drawer.modules)
    withDrawerImplementation(libs.drawer.location)
}
