plugins {
    alias(libs.plugins.android.library)
    id("band.effective.module.screen")
}

android {
    namespace = "band.effective.headlines.compose.article_details.screen"
}

dependencies {
    implementation(project(":screens:article-details:shared"))
}
