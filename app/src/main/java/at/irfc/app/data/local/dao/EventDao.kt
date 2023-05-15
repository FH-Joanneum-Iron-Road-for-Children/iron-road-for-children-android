package at.irfc.app.data.local.dao

import androidx.room.*
import at.irfc.app.data.local.IrfcDatabase
import at.irfc.app.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class EventDao(private val database: IrfcDatabase) {

    private val categoryDao get() = database.categoryDao()
    private val pictureDaoDao get() = database.pictureDao()
    private val locationDao get() = database.locationDao()

    @Transaction
    @Query("SELECT * FROM events")
    abstract fun getAll(): Flow<List<EventWithDetails>>

    @Transaction
    @Query("SELECT * FROM events WHERE id = :eventId")
    abstract fun getById(eventId: Long): Flow<EventWithDetails?>

    @Upsert
    protected abstract suspend fun upsert(events: List<Event>)

    @Query("DELETE FROM events WHERE id NOT IN (:idsToKeep)")
    protected abstract suspend fun deleteNotInList(idsToKeep: Set<Long>)

    @Query("DELETE FROM events WHERE id = :id")
    abstract suspend fun deleteById(id: Long)

    suspend fun upsertEvents(events: List<EventWithDetails>) {
        categoryDao.upsertCategories(
            events.map(EventWithDetails::category).distinctBy(EventCategory::id)
        )
        locationDao.upsert(
            events.map(EventWithDetails::location).distinctBy(EventLocation::id)
        )
        upsert(events.map(EventWithDetails::event))
        pictureDaoDao.upsert(events.flatMap(EventWithDetails::additionalImages))
    }

    suspend fun deleteNotInList(events: List<EventWithDetails>) {
        pictureDaoDao.deleteNotInList(
            events.flatMapTo(mutableSetOf()) {
                it.additionalImages.map(EventPicture::id)
            }
        )
        deleteNotInList(events.mapTo(mutableSetOf()) { it.event.id })
        categoryDao.deleteNotInList(events.mapTo(mutableSetOf()) { it.category.id })
        locationDao.deleteNotInList(events.mapTo(mutableSetOf()) { it.location.id })
    }

    @Transaction
    open suspend fun replaceEvents(events: List<EventWithDetails>) {
        upsertEvents(events)
        deleteNotInList(events)
    }

    suspend fun replaceEvent(event: EventWithDetails) = replaceEvents(listOf(event))
}
