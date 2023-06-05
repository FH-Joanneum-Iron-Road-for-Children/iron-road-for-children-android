package at.irfc.app.util

import android.util.Log
import at.irfc.app.R
import kotlinx.coroutines.flow.*

/**
 * Handles resources that come from a remote source (e.g. REST-API) and should be cached locally.
 *
 * Always tries to load from cache first and then check with the [shouldFetch] function,
 * if the cached data is stale and should be refreshed. If that's the case [fetch] will be called
 * and a [Resource.Loading] with the cached data gets emitted.
 * The result of [fetch] then gets passed to [update] which should update the cache. If [fetch] fails
 * a [Resource.Error] with the cached data and an errorMessage (string resource) is emitted.
 * Each change to the cache will be emitted.
 *
 * @param query responsible for subscribing to the cache and getting a flow of a database query
 * @param fetch responsible for fetching the newest data from a remote source. Only executed if
 *  [shouldFetch] returns true.
 * @param update responsible for inserting the returned data from [fetch] into the database
 * @param shouldFetch decides if the data from the database are stale
 */
inline fun <EntityType, ApiType> cachedRemoteResource(
    crossinline query: () -> Flow<EntityType>,
    crossinline fetch: suspend () -> ApiType,
    crossinline update: suspend (ApiType) -> Unit,
    crossinline shouldFetch: (EntityType) -> Boolean = { true }
): Flow<Resource<EntityType>> = flow {
    val cachedData = query().first()

    val flow = if (shouldFetch(cachedData)) {
        emit(Resource.Loading(cachedData))

        @Suppress("TooGenericExceptionCaught")
        try {
            update(fetch())
            query().map { Resource.Success(it) }
        } catch (tr: Throwable) {
            // TODO provide a better way to handle different errors
            //  also probably the string resource should not be specified here?
            Log.w("CachedRemoteResource", "Could not fetch data", tr)
            query().map {
                Resource.Error(StringResource(R.string.could_not_refresh_data_error), it)
            }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}
