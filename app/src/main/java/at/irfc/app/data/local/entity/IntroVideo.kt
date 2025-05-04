package at.irfc.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "intro_video")
data class IntroVideo(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "videoId")
    val id: Long,
    val altText: String,
    val path: String
)
