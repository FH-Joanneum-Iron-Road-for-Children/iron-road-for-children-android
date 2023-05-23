package at.irfc.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eventLocations")
data class EventLocation(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "eventLocationId")
    val id: Long,
    val name: String
)
