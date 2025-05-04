package at.irfc.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gallery")
data class Gallery(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "galleryId")
    val id: Long,
    val title: String,
    val path: String
)
