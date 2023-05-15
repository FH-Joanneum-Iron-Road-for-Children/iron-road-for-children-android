package at.irfc.app.data.local.dao

import androidx.room.*
import at.irfc.app.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureDao {

    @Query("SELECT * FROM eventPictures")
    fun getAll(): Flow<List<EventPicture>>

    @Query("SELECT * FROM eventPictures WHERE eventId = :eventId")
    fun getForEvent(eventId: Long): Flow<List<EventPicture>>

    @Query("SELECT * FROM eventPictures WHERE id = :pictureId")
    fun getById(pictureId: Long): Flow<EventPicture>

    @Upsert
    suspend fun upsert(pictures: List<EventPicture>)

    @Query("DELETE FROM eventPictures WHERE id NOT IN (:idsToKeep)")
    suspend fun deleteNotInList(idsToKeep: Set<Long>)
}
