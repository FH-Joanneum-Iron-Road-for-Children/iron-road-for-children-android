package at.irfc.app.data.local.dao

import androidx.room.*
import at.irfc.app.data.local.entity.SocialMedia
import kotlinx.coroutines.flow.Flow

@Dao
interface SocialMediaDao {
    @Query("SELECT * FROM socialMedia")
    fun getAll(): Flow<List<SocialMedia>>

    @Upsert
    suspend fun upsert(gallery: List<SocialMedia>)

    @Query("DELETE FROM socialMedia WHERE socialMediaId NOT IN (:idsToKeep)")
    suspend fun deleteNotInList(idsToKeep: Set<Long>)
}
