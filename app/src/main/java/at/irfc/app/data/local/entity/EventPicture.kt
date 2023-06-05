package at.irfc.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eventPictures")
data class EventPicture(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "eventPictureId")
    val id: Long,
    val title: String,
    val path: String,
    val eventId: Long
)
