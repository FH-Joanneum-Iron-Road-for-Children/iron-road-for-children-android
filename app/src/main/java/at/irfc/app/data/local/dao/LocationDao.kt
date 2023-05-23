package at.irfc.app.data.local.dao

import androidx.room.*
import at.irfc.app.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * FROM eventLocations")
    fun getAll(): Flow<List<EventLocation>>

    @Query("SELECT * FROM eventLocations WHERE eventLocationId = :locationId")
    fun getById(locationId: Long): Flow<EventLocation>

    @Upsert
    suspend fun upsert(categories: List<EventLocation>)

    @Query("DELETE FROM eventLocations WHERE eventLocationId NOT IN (:idsToKeep)")
    suspend fun deleteNotInList(idsToKeep: Set<Long>)
}
