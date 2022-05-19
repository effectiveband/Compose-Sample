plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

gradlePlugin {
    plugins {
        register("build-config-plugin") {
            id = "band.effective.build.config"
            implementationClass = "band.effective.plugins.BuildConfigPlugin"
        }
        register("screen-module-plugin") {
            id = "band.effective.module.screen"
            implementationClass = "band.effective.plugins.ScreenModulePlugin"
        }
        register("dagger-module-plugin") {
            id = "band.effective.module.dagger"
            implementationClass = "band.effective.plugins.DaggerModulePlugin"
        }
        register("compose-module-plugin") {
            id = "band.effective.module.compose"
            implementationClass = "band.effective.plugins.ComposeModulePlugin"
        }
    }
}

dependencies {
    implementation(libs.android.gradle)
    implementation(libs.kotlin.gradle)
    implementation(libs.google.ksp)
    compileOnly(gradleApi())
}
