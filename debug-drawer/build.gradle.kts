plugins {
    id("com.android.library")
    id("band.effective.module.dagger")
}

dependencies {

    implementation(project(":core"))
    implementation(project(":network"))

    implementation(Libs.Retrofit.client)
    implementation(Libs.Retrofit.mock)

    withDrawerImplementation(Libs.DebugDrawer.main)
    withDrawerImplementation(Libs.DebugDrawer.retrofit)
    withDrawerImplementation(Libs.DebugDrawer.okhttp)
    withDrawerImplementation(Libs.DebugDrawer.timber)
}
