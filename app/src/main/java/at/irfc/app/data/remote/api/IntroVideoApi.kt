package at.irfc.app.data.remote.api

import at.irfc.app.data.remote.dto.IntroVideoDto
import de.jensklingenberg.ktorfit.http.GET

interface IntroVideoApi {
    @GET("intro-video")
    suspend fun getVideo(): IntroVideoDto
}
