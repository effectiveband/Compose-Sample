buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Plugins.androidGradlePlugin)
        classpath(Plugins.kotlinGradlePlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
