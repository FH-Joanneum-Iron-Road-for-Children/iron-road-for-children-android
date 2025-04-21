package at.irfc.app.data.local.dao

import androidx.room.*
import at.irfc.app.data.local.entity.GalleryPicture
import kotlinx.coroutines.flow.Flow

@Dao
interface GalleryPictureDao {
    @Query("SELECT * FROM galleryPictures")
    fun getAll(): Flow<List<GalleryPicture>>

    @Upsert
    suspend fun upsert(pictures: List<GalleryPicture>)

    @Query("DELETE FROM galleryPictures WHERE galleryPictureId NOT IN (:idsToKeep)")
    suspend fun deleteNotInList(idsToKeep: Set<Long>)
}
