package at.irfc.app.data.local.entity.relations

import androidx.room.Entity

@Entity(primaryKeys = ["votingId", "eventId"], tableName = "votingEvent")
data class VotingEventCrossRef(
    val votingId: Long,
    val eventId: Long
)
