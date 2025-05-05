package at.irfc.app.data.local.dao

import androidx.room.*
import at.irfc.app.data.local.entity.IntroVideo
import kotlinx.coroutines.flow.Flow

@Dao
interface IntroVideoDao {

    @Query("SELECT * FROM intro_video LIMIT 1")
    fun get(): Flow<IntroVideo?>

    @Upsert
    suspend fun upsert(introVideo: IntroVideo)

    @Query("DELETE FROM intro_video WHERE videoId NOT IN (:idsToKeep)")
    suspend fun deleteNotInList(idsToKeep: Set<Long>)
}
