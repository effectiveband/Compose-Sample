object Libs {

    object Coroutines {
        const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Accompanist {
        const val insets = "com.google.accompanist:accompanist-insets:${Versions.Accompanist.version}"
        const val insetsUi ="com.google.accompanist:accompanist-insets-ui:${Versions.Accompanist.version}"
        const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:${Versions.Accompanist.version}"
        const val swipeRefreshLayout = "com.google.accompanist:accompanist-swiperefresh:${Versions.Accompanist.version}"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.AndroidX.core}"

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:${Versions.AndroidX.Activity.version}"
        }

        object Lifecycle {
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.Lifecycle.version}"
        }

        object Compose {
            const val ui = "androidx.compose.ui:ui:${Versions.AndroidX.Compose.version}"
            const val material = "androidx.compose.material:material:${Versions.AndroidX.Compose.version}"
            const val material3 = "androidx.compose.material3:material3:${Versions.AndroidX.Compose.material3}"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:${Versions.AndroidX.Compose.version}"

            const val tooling = "androidx.compose.ui:ui-tooling:${Versions.AndroidX.Compose.version}"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.AndroidX.Compose.version}"
            const val composeUiManifest = "androidx.compose.ui:ui-test-manifest:${Versions.AndroidX.Compose.version}"
        }

        object Test {
            const val junit = "junit:junit:${Versions.AndroidX.Test.junit}"
            const val junitExt = "androidx.test.ext:junit:${Versions.AndroidX.Test.junitExt}"
            const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.AndroidX.Compose.version}"
            const val composeUiJunit = "androidx.compose.ui:ui-test-junit4:${Versions.AndroidX.Compose.version}"
        }
    }

    object Navigation {
        const val destinations = "io.github.raamcosta.compose-destinations:core:${Versions.Navigation.version}"
        const val compiler = "io.github.raamcosta.compose-destinations:ksp:${Versions.Navigation.version}"
    }
}
