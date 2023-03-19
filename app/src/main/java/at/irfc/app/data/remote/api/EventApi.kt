package at.irfc.app.data.remote.api

import at.irfc.app.data.remote.dto.EventDto
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

interface EventApi {
    @GET("events")
    suspend fun getEvents(): List<EventDto>

    @GET("events/{eventId}")
    suspend fun getEvent(@Path("eventId") id: String): EventDto
}
