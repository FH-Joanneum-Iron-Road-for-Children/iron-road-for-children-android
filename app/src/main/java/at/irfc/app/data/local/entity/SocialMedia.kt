package at.irfc.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "socialMedia")
data class SocialMedia(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "socialMediaId")
    val id: Long,
    val title: String,
    val path: String
)
