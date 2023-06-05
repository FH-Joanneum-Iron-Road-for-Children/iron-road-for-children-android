package at.irfc.app.util

sealed class Resource<T> {
    abstract val data: T?

    data class Success<T>(override val data: T) : Resource<T>()
    data class Loading<T>(override val data: T? = null) : Resource<T>()
    data class Error<T>(
        val errorMessage: StringResource,
        override val data: T? = null
    ) : Resource<T>()
}
