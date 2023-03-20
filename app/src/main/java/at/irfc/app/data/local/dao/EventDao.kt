package at.irfc.app.data.local.dao

import androidx.room.*
import at.irfc.app.data.local.entity.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    fun getEvents(): Flow<List<Event>>

    @Upsert
    suspend fun upsertEvents(events: List<Event>)

    @Query("DELETE FROM events WHERE id NOT IN (:idsToKeep)")
    suspend fun deleteNotInList(idsToKeep: Set<String>)

    @Transaction
    suspend fun replaceEvents(events: List<Event>) {
        upsertEvents(events)
        deleteNotInList(events.mapTo(mutableSetOf(), Event::id))
    }
}
