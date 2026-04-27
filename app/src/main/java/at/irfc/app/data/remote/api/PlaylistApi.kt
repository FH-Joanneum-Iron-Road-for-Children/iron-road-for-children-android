package at.irfc.app.data.remote.api

import at.irfc.app.data.remote.dto.PlaylistDto
import de.jensklingenberg.ktorfit.http.GET

interface PlaylistApi {
    @GET("playlist")
    suspend fun getPlaylist(): PlaylistDto?
}
