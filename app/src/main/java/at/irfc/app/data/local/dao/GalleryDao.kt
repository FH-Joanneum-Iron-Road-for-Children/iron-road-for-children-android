package at.irfc.app.data.local.dao

import androidx.room.*
import at.irfc.app.data.local.entity.Gallery
import kotlinx.coroutines.flow.Flow

@Dao
interface GalleryDao {
    @Query("SELECT * FROM gallery")
    fun getAll(): Flow<List<Gallery>>

    @Upsert
    suspend fun upsert(gallery: List<Gallery>)

    @Query("DELETE FROM gallery WHERE galleryId NOT IN (:idsToKeep)")
    suspend fun deleteNotInList(idsToKeep: Set<Long>)
}
