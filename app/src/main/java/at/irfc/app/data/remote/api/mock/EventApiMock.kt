package at.irfc.app.data.remote.api.mock

import at.irfc.app.data.remote.api.EventApi
import at.irfc.app.data.remote.dto.EventDto
import kotlinx.coroutines.delay

class EventApiMock : EventApi {
    override suspend fun getEvents(): List<EventDto> {
        delay(500) // Simulate some network latency
        return events
    }

    override suspend fun getEvent(id: Long) = getEvents().firstOrNull { it.eventId == id }

    companion object {
        val events = listOf(
            EventDto(
                eventId = 1,
                title = "HÖRST",
                eventInfo = EventDto.InfoDto(
                    infoText = "lorem ipsum",
                    pictures = listOf(
                        EventDto.PictureDto(
                            pictureId = 2,
                            altText = "HÖRST Album 1",
                            // Wrong ratio for testing
                            path = "https://picsum.photos/500/300?random=2"
                        ),
                        EventDto.PictureDto(
                            pictureId = 3,
                            altText = "HÖRST Album 2",
                            path = "https://picsum.photos/400/300?random=3"
                        )
                    )
                ),
                picture = EventDto.PictureDto(
                    pictureId = 1,
                    altText = "HÖRST Cover",
                    path = "https://picsum.photos/350/100?random=1" // Wrong ratio for testing
                ),
                startDateTimeInUTC = 1690542000,
                endDateTimeInUTC = 1690549200,
                eventLocation = EventDto.LocationDto(
                    eventLocationId = 1,
                    name = "Family Rock Stage"
                ),
                eventCategory = EventDto.CategoryDto(eventCategoryId = 1, name = "Band")
            ),
            EventDto(
                eventId = 2,
                title = "THE AWEZOMBIES",
                eventInfo = EventDto.InfoDto(
                    infoText = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed " +
                        "diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam",
                    pictures = listOf(
                        EventDto.PictureDto(
                            pictureId = 4,
                            altText = "THE AWEZOMBIES Album 1",
                            path = "https://picsum.photos/400/300?random=4"
                        ),
                        EventDto.PictureDto(
                            pictureId = 5,
                            altText = "THE AWEZOMBIES Album 2",
                            path = "https://picsum.photos/400/300?random=5"
                        ),
                        EventDto.PictureDto(
                            pictureId = 20,
                            altText = "THE AWEZOMBIES Album 3",
                            path = "https://picsum.photos/400/300?random=20"
                        )
                    )
                ),
                picture = EventDto.PictureDto(
                    6,
                    "THE AWEZOMBIES Cover",
                    "https://picsum.photos/250/100?random=6"
                ),
                startDateTimeInUTC = 1690725600,
                endDateTimeInUTC = 1690729200,
                eventLocation = EventDto.LocationDto(eventLocationId = 2, name = "Rock Stage"),
                eventCategory = EventDto.CategoryDto(eventCategoryId = 1, name = "Band")
            ),
            EventDto(
                eventId = 3,
                title = "BURNSWELL",
                eventInfo = EventDto.InfoDto(
                    infoText = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed " +
                        "diam nonumy eirmod tempor invidunt ut labore et dolore magna " +
                        "aliquyam erat",
                    pictures = emptyList()
                ),
                picture = EventDto.PictureDto(
                    pictureId = 7,
                    altText = "BURNSWELL Cover",
                    path = "https://picsum.photos/250/100?random=7"
                ),
                startDateTimeInUTC = 1690635600,
                endDateTimeInUTC = 1690639200,
                eventLocation = EventDto.LocationDto(eventLocationId = 2, name = "Rock Stage"),
                eventCategory = EventDto.CategoryDto(eventCategoryId = 1, name = "Band")
            ),
            EventDto(
                eventId = 4,
                title = "GLEN AMPLE",
                eventInfo = EventDto.InfoDto(
                    infoText = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr",
                    pictures = emptyList()
                ),
                picture = EventDto.PictureDto(
                    pictureId = 8,
                    altText = "GLEN AMPLE Artist",
                    path = "https://picsum.photos/250/100?random=8"
                ),
                startDateTimeInUTC = 1690549200,
                endDateTimeInUTC = 1690552800,
                eventLocation = EventDto.LocationDto(eventLocationId = 3, name = "Tattoo area"),
                eventCategory = EventDto.CategoryDto(eventCategoryId = 2, name = "Tattoo")
            ),
            EventDto(
                eventId = 5,
                title = "Tattoo artist 1",
                eventInfo = EventDto.InfoDto(
                    infoText = "+43 664 123456",
                    pictures = emptyList()
                ),
                picture = EventDto.PictureDto(
                    pictureId = 8,
                    altText = "Artist",
                    path = "https://picsum.photos/250/100?random=9"
                ),
                startDateTimeInUTC = 1690531200,
                endDateTimeInUTC = 1690653600,
                eventLocation = EventDto.LocationDto(eventLocationId = 3, name = "Tattoo area"),
                eventCategory = EventDto.CategoryDto(eventCategoryId = 2, name = "Tattoo")
            )
        )
    }
}
