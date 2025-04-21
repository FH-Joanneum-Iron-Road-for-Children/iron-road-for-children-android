package at.irfc.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "galleryPictures")
data class GalleryPicture(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "galleryPictureId")
    val id: Long,
    val title: String,
    val path: String
)
