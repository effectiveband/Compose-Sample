plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
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
        register("network-config-plugin") {
            id = "band.effective.network.config"
            implementationClass = "band.effective.plugins.NetworkConfigPlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:1.6.10-1.0.4")
}
