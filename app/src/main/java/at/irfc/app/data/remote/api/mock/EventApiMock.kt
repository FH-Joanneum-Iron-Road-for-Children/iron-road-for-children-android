package at.irfc.app.data.remote.api.mock

import at.irfc.app.data.remote.api.EventApi
import at.irfc.app.data.remote.dto.EventDto
import kotlinx.coroutines.delay

class EventApiMock : EventApi {
    val events = listOf(
        EventDto(
            eventId = 1,
            title = "HÖRST",
            eventInfo = EventDto.InfoDto(
                infoText = "lorem ipsum",
                pictures = listOf(
                    EventDto.PictureDto(
                        pictureId = 2,
                        title = "HÖRST Album 1",
                        path = "https://picsum.photos/400/300?random=2"
                    ),
                    EventDto.PictureDto(
                        pictureId = 3,
                        title = "HÖRST Album 2",
                        path = "https://picsum.photos/400/300?random=3"
                    )
                )
            ),
            image = EventDto.PictureDto(
                pictureId = 1,
                title = "HÖRST Cover",
                path = "https://picsum.photos/250/100?random=1"
            ),
            startDateTimeInUTC = 1689580800,
            endDateTimeInUTC = 1689584400,
            eventLocation = EventDto.LocationDto(eventLocationId = 1, name = "Family Rock Stage"),
            eventCategory = EventDto.CategoryDto(id = 1, name = "Band")
        ),
        EventDto(
            eventId = 2,
            title = "THE AWEZOMBIES",
            eventInfo = EventDto.InfoDto(
                infoText = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam " +
                    "nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam",
                pictures = listOf(
                    EventDto.PictureDto(
                        pictureId = 4,
                        title = "THE AWEZOMBIES Album 1",
                        path = "https://picsum.photos/400/300?random=4"
                    ),
                    EventDto.PictureDto(
                        pictureId = 5,
                        title = "THE AWEZOMBIES Album 2",
                        path = "https://picsum.photos/400/300?random=5"
                    ),
                    EventDto.PictureDto(
                        pictureId = 20,
                        title = "THE AWEZOMBIES Album 3",
                        path = "https://picsum.photos/400/300?random=20"
                    )
                )
            ),
            image = EventDto.PictureDto(
                6,
                "THE AWEZOMBIES Cover",
                "https://picsum.photos/250/100?random=6"
            ),
            startDateTimeInUTC = 1689580800,
            endDateTimeInUTC = 1689584400,
            eventLocation = EventDto.LocationDto(eventLocationId = 2, name = "Rock Stage"),
            eventCategory = EventDto.CategoryDto(id = 1, name = "Band")
        ),
        EventDto(
            eventId = 3,
            title = "BURNSWELL",
            eventInfo = EventDto.InfoDto(
                infoText = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam " +
                    "nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat",
                pictures = emptyList()
            ),
            image = EventDto.PictureDto(
                pictureId = 7,
                title = "BURNSWELL Cover",
                path = "https://picsum.photos/250/100?random=7"
            ),
            startDateTimeInUTC = 1689584400,
            endDateTimeInUTC = 1689588000,
            eventLocation = EventDto.LocationDto(eventLocationId = 2, name = "Rock Stage"),
            eventCategory = EventDto.CategoryDto(id = 1, name = "Band")
        ),
        EventDto(
            eventId = 4,
            title = "GLEN AMPLE",
            eventInfo = EventDto.InfoDto(
                infoText = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr",
                pictures = emptyList()
            ),
            image = EventDto.PictureDto(
                pictureId = 8,
                title = "BURNSWELL Cover",
                path = "https://picsum.photos/250/100?random=8"
            ),
            startDateTimeInUTC = 1689584400,
            endDateTimeInUTC = 1689588000,
            eventLocation = EventDto.LocationDto(eventLocationId = 3, name = "Tattoo area"),
            eventCategory = EventDto.CategoryDto(id = 2, name = "Tattoo")
        )
    )

    override suspend fun getEvents(): List<EventDto> {
        delay(500) // Simulate some network latency
        return events
    }

    override suspend fun getEvent(id: Long) = getEvents().firstOrNull { it.eventId == id }
}
