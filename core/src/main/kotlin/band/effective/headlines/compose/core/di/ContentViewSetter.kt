package band.effective.headlines.compose.core.di

import android.app.Activity
import android.view.View

fun interface ContentViewSetter {
    fun setContentView(activity: Activity, view: View)
}
