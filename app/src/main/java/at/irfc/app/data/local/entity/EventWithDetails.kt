package at.irfc.app.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class EventWithDetails(
    @Embedded val event: Event,

    @Relation(parentColumn = "categoryId", entityColumn = "id")
    val category: EventCategory,

    @Relation(parentColumn = "locationId", entityColumn = "id")
    val location: EventLocation,

    @Relation(parentColumn = "id", entityColumn = "eventId")
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
