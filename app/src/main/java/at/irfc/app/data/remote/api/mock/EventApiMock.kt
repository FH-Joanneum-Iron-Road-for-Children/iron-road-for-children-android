package at.irfc.app.data.remote.api.mock

import at.irfc.app.data.remote.api.EventApi
import at.irfc.app.data.remote.dto.EventDto

class EventApiMock : EventApi {
    private val events = listOf(
        EventDto(id = "1", title = "HÖRST", description = "lorem ipsum"),
        EventDto(id = "2", title = "THE AWEZOMBIES", description = "lorem ipsum"),
        EventDto(id = "3", title = "BURNSWELL", description = "lorem ipsum"),
        EventDto(id = "4", title = "GLEN AMPLE", description = "lorem ipsum")
    )

    override suspend fun getEvents(): List<EventDto> = events

    override suspend fun getEvent(id: String) = events.firstOrNull { it.id == id }
}
