@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
    }
}

rootProject.name = "Headlines Compose"

include(":app")
include(":core", ":core-ui", ":debug-drawer", ":network", ":news-api")
include(
    ":screens:feed",
    ":screens:search",
    ":screens:about",
    ":screens:article-details:screen",
    ":screens:article-details:shared"
)
