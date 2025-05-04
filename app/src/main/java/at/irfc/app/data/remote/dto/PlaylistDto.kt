package at.irfc.app.data.remote.dto

import at.irfc.app.data.local.entity.Playlist
import kotlinx.serialization.Serializable

@Serializable
class PlaylistDto(
    val playlistId: Long,
    val title: String,
    val spotifyPlaylistId: String
)

fun PlaylistDto.toPlaylist(): Playlist {
    return Playlist(
        id = this.playlistId,
        title = this.title,
        spotifyId = this.spotifyPlaylistId
    )
}
