package at.irfc.app.data.remote.api

import at.irfc.app.data.remote.dto.SocialMediaDto
import de.jensklingenberg.ktorfit.http.GET

interface SocialMediaApi {
    @GET("socialMedias")
    suspend fun getSocialMedia(): List<SocialMediaDto>
}
