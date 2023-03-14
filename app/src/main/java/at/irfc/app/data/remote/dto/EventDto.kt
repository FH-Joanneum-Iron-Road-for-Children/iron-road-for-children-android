package at.irfc.app.data.remote.dto

import at.irfc.app.data.local.entity.Event
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class EventDto(
    val id: String,
    val title: String
)

fun EventDto.toEventEntity(): Event = Event(
    id = this.id,
    title = this.title,
    updated = Date()
)