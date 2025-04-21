package at.irfc.app.data.remote.api

import at.irfc.app.data.remote.dto.PicturesDto
import de.jensklingenberg.ktorfit.http.GET

interface PictureApi {
    @GET("pictures")
    suspend fun getPictures(): List<PicturesDto>
}
