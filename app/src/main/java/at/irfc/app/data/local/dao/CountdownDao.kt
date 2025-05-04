package at.irfc.app.data.local.dao

import androidx.room.*
import at.irfc.app.data.local.entity.Countdown
import kotlinx.coroutines.flow.Flow

@Dao
interface CountdownDao {
    @Query("SELECT * FROM countdown")
    fun getAll(): Flow<List<Countdown>>

    @Upsert
    suspend fun upsert(countdown: List<Countdown>)

    @Query("DELETE FROM countdown WHERE countdownId NOT IN (:idsToKeep)")
    suspend fun deleteNotInList(idsToKeep: Set<Long>)
}
