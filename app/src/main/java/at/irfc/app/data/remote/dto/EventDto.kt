package at.irfc.app.data.remote.dto

import at.irfc.app.data.local.entity.Event
import at.irfc.app.data.local.entity.EventCategory
import at.irfc.app.data.local.entity.EventLocation
import at.irfc.app.data.local.entity.EventPicture
import at.irfc.app.data.local.entity.EventWithDetails
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import kotlinx.serialization.Serializable

@Serializable
class EventDto(
    val eventId: Long,
    val title: String,
    val eventInfo: InfoDto,
    val image: PictureDto,
    val startDateTimeInUTC: Long,
    val endDateTimeInUTC: Long,
    val eventLocation: LocationDto,
    val eventCategory: CategoryDto
) {
    @Serializable
    class InfoDto(
        val infoText: String,
        val pictures: List<PictureDto>
    )

    @Serializable
    class PictureDto(
        val pictureId: Long,
        val title: String,
        val path: String
    )

    @Serializable
    class LocationDto(
        val eventLocationId: Long,
        val name: String
    )

    @Serializable
    class CategoryDto(
        val id: Long,
        val name: String
    )
}

fun EventDto.toEventEntity(): EventWithDetails {
    val category = EventCategory(id = this.eventCategory.id, name = this.eventCategory.name)
    val pictures = this.eventInfo.pictures.map {
        EventPicture(id = it.pictureId, title = it.title, path = it.path, eventId = this.eventId)
    }
    val location = EventLocation(
        id = this.eventLocation.eventLocationId,
        name = this.eventLocation.name
    )

    return EventWithDetails(
        event = Event(
            id = this.eventId,
            title = this.title,
            startDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(this.startDateTimeInUTC),
                ZoneId.of("Europe/Berlin")
            ),
            endDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(this.endDateTimeInUTC),
                ZoneId.of("Europe/Berlin")
            ),
            description = this.eventInfo.infoText,
            image = Event.Image(this.image.title, this.image.path),
            categoryId = category.id,
            locationId = location.id,
            updated = LocalDateTime.now()
        ),
        category = category,
        location = location,
        additionalImages = pictures
    )
}
