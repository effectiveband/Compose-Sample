plugins {
    id("com.android.library")
    id("band.effective.module.dagger")
    id("band.effective.network.config")
    id("com.google.devtools.ksp")
}

dependencies {
    implementation(project(":core"))

    implementation(Libs.Retrofit.client)
    implementation(Libs.Retrofit.moshiConverter)

    implementation(Libs.OkHttp.client)
    implementation(Libs.OkHttp.loggingInterceptor)

    implementation(Libs.Moshi.kotlin)
    implementation(Libs.Moshi.adapters)
    ksp(Libs.Moshi.compiler)
}
