package at.irfc.app.data.repository

import at.irfc.app.data.local.dao.CategoryDao
import at.irfc.app.data.local.dao.EventDao
import at.irfc.app.data.local.entity.EventCategory
import at.irfc.app.data.local.entity.EventWithDetails
import at.irfc.app.data.remote.api.EventApi
import at.irfc.app.data.remote.dto.EventDto
import at.irfc.app.data.remote.dto.toEventEntity
import at.irfc.app.util.Resource
import at.irfc.app.util.cachedRemoteResource
import at.irfc.app.util.extensions.minus
import java.util.*
import kotlin.time.Duration.Companion.hours
import kotlinx.coroutines.flow.Flow

class EventRepository(
    private val eventDao: EventDao,
    private val categoryDao: CategoryDao,
    private val eventApi: EventApi
) {
    fun loadEvents(force: Boolean): Flow<Resource<List<EventWithDetails>>> = cachedRemoteResource(
        query = eventDao::getAll,
        fetch = eventApi::getEvents,
        update = { eventDao.replaceEvents(it.map(EventDto::toEventEntity)) },
        shouldFetch = { events ->
            val updateWhenOlderThan = Date() - cacheDuration
            force || events.isEmpty() || events.any { it.updated.before(updateWhenOlderThan) }
        }
    )

    fun loadEvent(force: Boolean, id: Long): Flow<Resource<EventWithDetails>> {
        return cachedRemoteResource(
            query = { eventDao.getById(id) },
            fetch = { eventApi.getEvent(id) ?: throw Exception() },
            update = { eventDao.replaceEvents(listOf(it.toEventEntity())) },
            shouldFetch = { event ->
                val updateWhenOlderThan = Date() - cacheDuration
                force || event == null || event.updated.before(updateWhenOlderThan)
            }
        )
    }

    fun getCategories(): Flow<List<EventCategory>> = categoryDao.getAll()

    companion object {
        val cacheDuration = 2.hours
    }
}
