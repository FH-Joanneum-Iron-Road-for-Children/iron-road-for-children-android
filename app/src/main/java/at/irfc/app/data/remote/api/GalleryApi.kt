package at.irfc.app.data.remote.api

import at.irfc.app.data.remote.dto.GalleryDto
import de.jensklingenberg.ktorfit.http.GET

interface GalleryApi {
    @GET("gallery")
    suspend fun getGallery(): List<GalleryDto>
}
