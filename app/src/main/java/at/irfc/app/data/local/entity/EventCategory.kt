package at.irfc.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eventCategories")
data class EventCategory(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "eventCategoryId")
    val id: Long,
    val name: String
)
