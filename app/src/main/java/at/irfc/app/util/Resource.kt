package at.irfc.app.util

import androidx.annotation.StringRes

sealed class Resource<T : Any> {
    abstract val data: T?

    class Success<T : Any>(override val data: T) : Resource<T>()
    class Error<T : Any>(@StringRes val errorMessage: Int, override val data: T? = null) :
        Resource<T>()

    class Loading<T : Any>(override val data: T? = null) : Resource<T>()
}
