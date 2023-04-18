package at.irfc.app.data.local.dao

import androidx.room.*
import at.irfc.app.data.local.entity.EventCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM eventCategories")
    fun getAll(): Flow<List<EventCategory>>

    @Query("SELECT * FROM eventCategories WHERE id = :categoryId")
    fun getById(categoryId: Long): Flow<EventCategory>

    @Upsert
    suspend fun upsertCategories(categories: List<EventCategory>)

    @Query("DELETE FROM eventCategories WHERE id NOT IN (:idsToKeep)")
    suspend fun deleteNotInList(idsToKeep: Set<Long>)
}
