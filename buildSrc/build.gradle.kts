plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("android-app-plugin") {
            id = "band.effective.app"
            implementationClass = "band.effective.plugins.AndroidAppPlugin"
        }
        register("screen-module-plugin") {
            id = "band.effective.module.screen"
            implementationClass = "band.effective.plugins.ScreenModulePlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
}
