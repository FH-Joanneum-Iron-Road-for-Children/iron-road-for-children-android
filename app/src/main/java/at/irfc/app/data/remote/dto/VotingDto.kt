package at.irfc.app.data.remote.dto

import at.irfc.app.data.local.entity.ServerVoting
import at.irfc.app.data.local.entity.ServerVotingWithEvents
import java.time.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
class VotingDto(
    val votingId: Long,
    val title: String,
    val active: Boolean,
    val events: List<EventDto>
)

fun VotingDto.toVotingEntity(): ServerVotingWithEvents = ServerVotingWithEvents(
    voting = this.toServerVoting(),
    events = this.events.map { it.toEventEntity() }
)

private fun VotingDto.toServerVoting(): ServerVoting = ServerVoting(
    id = this.votingId,
    title = this.title,
    isActive = this.active,
    updated = LocalDateTime.now()
)
