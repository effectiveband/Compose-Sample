package band.effective.headlines.compose.core.di

import android.content.Context
import band.effective.headlines.compose.core.di.string_resolver.StringResolver

interface CommonDependencies: ComponentDependencies {
    val context: Context
    val stringResolver: StringResolver
}
