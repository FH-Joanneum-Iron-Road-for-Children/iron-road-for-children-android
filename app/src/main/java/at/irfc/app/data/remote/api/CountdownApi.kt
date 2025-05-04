package at.irfc.app.data.remote.api

import at.irfc.app.data.remote.dto.CountdownDto
import de.jensklingenberg.ktorfit.http.GET

interface CountdownApi {
    @GET("countdowns")
    suspend fun getCountdown(): List<CountdownDto>
}
