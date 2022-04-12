plugins {
    id("com.android.library")
    id("band.effective.module.dagger")
    id("com.google.devtools.ksp")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":network"))

    implementation(Libs.AndroidX.Paging.runtime)

    implementation(Libs.Retrofit.client)
    implementation(Libs.Retrofit.moshiConverter)

    implementation(Libs.Moshi.kotlin)
    implementation(Libs.Moshi.adapters)
    ksp(Libs.Moshi.compiler)
}
