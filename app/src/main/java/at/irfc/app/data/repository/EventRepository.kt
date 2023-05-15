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
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.hours
import kotlin.time.toJavaDuration
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
            val updateWhenOlderThan = LocalDateTime.now() - cacheDuration
            force || events.isEmpty() || events.any { it.updated.isBefore(updateWhenOlderThan) }
        }
    )

    fun loadEvent(force: Boolean, id: Long): Flow<Resource<EventWithDetails?>> {
        return cachedRemoteResource(
            query = { eventDao.getById(id) },
            fetch = { eventApi.getEvent(id) },
            update = { event ->
                if (event == null) {
                    eventDao.deleteById(id)
                } else {
                    eventDao.replaceEvent(event.toEventEntity())
                }
            },
            shouldFetch = { event ->
                val updateWhenOlderThan = LocalDateTime.now() - cacheDuration
                force || event == null || event.updated.isBefore(updateWhenOlderThan)
            }
        )
    }

    fun getCategories(): Flow<List<EventCategory>> = categoryDao.getAll()

    companion object {
        val cacheDuration = 2.hours.toJavaDuration()
    }
}
