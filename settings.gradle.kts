@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Headlines Compose"

include(":app")
include(":core", ":core-ui", ":network", ":news-api")
include(":screens:feed", ":screens:search", ":screens:about", ":screens:news-details")
