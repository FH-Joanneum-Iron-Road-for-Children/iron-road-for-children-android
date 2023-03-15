package at.irfc.app.data.repository

import at.irfc.app.data.local.dao.EventDao
import at.irfc.app.data.local.entity.Event
import at.irfc.app.data.remote.api.EventApi
import at.irfc.app.data.remote.dto.EventDto
import at.irfc.app.data.remote.dto.toEventEntity
import at.irfc.app.util.Resource
import at.irfc.app.util.cachedRemoteResource
import at.irfc.app.util.extensions.minus
import kotlinx.coroutines.flow.Flow
import java.util.*
import kotlin.time.Duration.Companion.hours

class EventRepository(
    private val eventDao: EventDao,
    private val eventApi: EventApi
) {
    fun loadEvents(force: Boolean): Flow<Resource<List<Event>>> = cachedRemoteResource(
        query = eventDao::getEvents,
        fetch = eventApi::getEvents,
        update = { eventDao.replaceEvents(it.map(EventDto::toEventEntity)) },
        shouldFetch = { events ->
            val updateWhenOlderThan = Date() - cacheDuration
            force || events.isEmpty() || events.any { it.updated.before(updateWhenOlderThan) }
        },
    )

    companion object {
        val cacheDuration = 2.hours
    }
}