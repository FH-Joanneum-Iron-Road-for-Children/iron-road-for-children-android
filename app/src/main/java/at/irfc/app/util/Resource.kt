package at.irfc.app.util

import androidx.annotation.StringRes

sealed class Resource<T : Any> {
    abstract val data: T?

    data class Success<T : Any>(override val data: T) : Resource<T>()
    data class Loading<T : Any>(override val data: T? = null) : Resource<T>()
    data class Error<T : Any>(
        @StringRes val errorMessage: Int,
        override val data: T? = null
    ) : Resource<T>()
}
