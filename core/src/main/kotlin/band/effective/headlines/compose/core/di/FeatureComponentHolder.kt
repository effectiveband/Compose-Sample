package band.effective.headlines.compose.core.di

fun <T : Any, A> featureComponent(creator: (A) -> T): FeatureComponentHolder<T, A> {
    return FeatureComponentHolder(creator, {})
}

fun <T : Any, A> featureComponent(creator: (A) -> T, onClear: (T) -> Unit): FeatureComponentHolder<T, A> {
    return FeatureComponentHolder(creator, onClear)
}

open class FeatureComponentHolder<out T : Any, in A>(private val creator: (A) -> T, private val onClear: (T) -> Unit) {
    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator(arg)
                instance = created
                created
            }
        }
    }

    fun getIfCreated() = instance

    fun clearInstance() {
        synchronized(this) {
            val currentInstance = instance
            if (currentInstance != null) {
                onClear(currentInstance)
            }
            instance = null
        }
    }
}
