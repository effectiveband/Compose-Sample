package band.effective.headlines.compose.core.di.string_resolver

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface StringResolver {
    fun getString(@StringRes res: Int): String
    fun getString(@StringRes res: Int, vararg formatArgs: Any): String
    fun getQuantityString(@PluralsRes res: Int, quantity: Int): String
}
