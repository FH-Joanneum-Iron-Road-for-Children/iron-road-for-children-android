package at.irfc.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist")
data class Playlist(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "playlistId")
    val id: Long,
    val title: String,
    val spotifyId: String
)
