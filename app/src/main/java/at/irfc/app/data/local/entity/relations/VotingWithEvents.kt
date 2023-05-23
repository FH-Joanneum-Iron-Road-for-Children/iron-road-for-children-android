package at.irfc.app.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import at.irfc.app.data.local.entity.Event
import at.irfc.app.data.local.entity.Voting

data class VotingWithEvents(
    @Embedded val voting: Voting,

    @Relation(
        parentColumn = "votingId",
        entityColumn = "eventId",
        associateBy = Junction(VotingEventCrossRef::class)
    )
    val events: List<Event>
)
