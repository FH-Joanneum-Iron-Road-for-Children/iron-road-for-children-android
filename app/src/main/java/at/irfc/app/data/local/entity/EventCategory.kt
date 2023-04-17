package at.irfc.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eventCategories")
data class EventCategory(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val name: String
)
