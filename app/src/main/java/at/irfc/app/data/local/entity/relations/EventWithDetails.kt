package at.irfc.app.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import at.irfc.app.data.local.entity.Event
import at.irfc.app.data.local.entity.EventCategory
import at.irfc.app.data.local.entity.EventLocation
import at.irfc.app.data.local.entity.EventPicture

data class EventWithDetails(
    @Embedded val event: Event,

    @Relation(parentColumn = "categoryId", entityColumn = "eventCategoryId")
    val category: EventCategory,

    @Relation(parentColumn = "locationId", entityColumn = "eventLocationId")
    val location: EventLocation,

    @Relation(parentColumn = "eventId", entityColumn = "eventPictureId")
    val additionalImages: List<EventPicture>
) {
    // Shorthand accessors
    inline val id get() = event.id
    inline val title get() = event.title
    inline val startDateTime get() = event.startDateTime
    inline val endDateTime get() = event.endDateTime
    inline val description get() = event.description
    inline val image get() = event.image
    inline val updated get() = event.updated
}
