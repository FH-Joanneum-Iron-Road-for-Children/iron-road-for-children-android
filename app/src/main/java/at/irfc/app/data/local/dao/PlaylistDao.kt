package at.irfc.app.data.local.dao

import androidx.room.*
import at.irfc.app.data.local.entity.Playlist
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM playlist")
    fun getAll(): Flow<Playlist>

    @Upsert
    suspend fun upsert(playlist: Playlist)

    @Query("DELETE FROM playlist WHERE playlistId NOT IN (:idsToKeep)")
    suspend fun deleteNotInList(idsToKeep: Set<Long>)
}
