@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://effective-android.bytesafe.dev/maven/drawer/")
        }
    }
}

rootProject.name = "Headlines Compose"

include(":app")
include(":core", ":core-ui", ":debug-drawer", ":network", ":news-api")
include(":screens:feed", ":screens:search", ":screens:about", ":screens:article-details")
