package at.irfc.app.util

import androidx.annotation.StringRes

sealed class Resource<T> {
    abstract val data: T?

    data class Success<T>(override val data: T) : Resource<T>()
    data class Loading<T>(override val data: T? = null) : Resource<T>()
    data class Error<T>(
        @StringRes val errorMessage: Int,
        override val data: T? = null
    ) : Resource<T>()
}
