package band.effective.headlines.compose.drawer

import band.effective.headlines.compose.core.di.ContentViewSetter
import dagger.Module
import dagger.Provides

@Module
interface DrawerModule {

    companion object {
        @Provides
        fun provideContentViewSetter(): ContentViewSetter = ContentViewSetter { activity, view ->
            activity.setContentView(view)
        }
    }
}
