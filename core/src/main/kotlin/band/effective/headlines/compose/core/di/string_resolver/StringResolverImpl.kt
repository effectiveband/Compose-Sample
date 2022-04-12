package band.effective.headlines.compose.core.di.string_resolver

import android.content.Context
import dagger.Reusable

@Reusable
internal class StringResolverImpl(private val context: Context) : StringResolver {
    override fun getString(res: Int) = context.getString(res)
    override fun getString(res: Int, vararg formatArgs: Any) = context.getString(res, *formatArgs)

    override fun getQuantityString(res: Int, quantity: Int) =
        context.resources.getQuantityString(res, quantity, quantity)
}
