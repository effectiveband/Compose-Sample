object Libs {

    object Coil {
        const val coil = "io.coil-kt:coil-compose:${Versions.Coil.version}"
    }

    object Timber {
        const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    }

    object Retrofit {
        const val client = "com.squareup.retrofit2:retrofit:${Versions.Retrofit.version}"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.Retrofit.version}"
    }

    object OkHttp {
        const val client = "com.squareup.okhttp3:okhttp:${Versions.OkHttp.version}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.OkHttp.version}"
    }

    object Moshi {
        const val kotlin = "com.squareup.moshi:moshi-kotlin:${Versions.Moshi.version}"
        const val compiler = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.Moshi.version}"
        const val adapters = "com.squareup.moshi:moshi-adapters:${Versions.Moshi.version}"
    }

    object Dagger2 {
        const val annotations = "com.google.dagger:dagger:${Versions.Dagger2.version}"
        const val compiler = "com.google.dagger:dagger-compiler:${Versions.Dagger2.version}"
    }

    object Coroutines {
        const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Accompanist {
        const val insets = "com.google.accompanist:accompanist-insets:${Versions.Accompanist.version}"
        const val insetsUi ="com.google.accompanist:accompanist-insets-ui:${Versions.Accompanist.version}"
        const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:${Versions.Accompanist.version}"
        const val swipeRefreshLayout = "com.google.accompanist:accompanist-swiperefresh:${Versions.Accompanist.version}"
        const val placeholer = "com.google.accompanist:accompanist-placeholder-material:${Versions.Accompanist.version}"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.AndroidX.core}"

        object Paging {
            const val runtime = "androidx.paging:paging-runtime:${Versions.AndroidX.Paging.version}"
            const val compose = "androidx.paging:paging-compose:${Versions.AndroidX.Paging.compose}"
        }

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:${Versions.AndroidX.Activity.version}"
        }

        object Lifecycle {
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.Lifecycle.version}"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.Lifecycle.version}"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.AndroidX.Lifecycle.version}"
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
