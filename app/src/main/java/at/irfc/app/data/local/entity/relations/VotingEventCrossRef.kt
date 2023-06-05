package at.irfc.app.data.local.entity.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["votingId", "eventId"], tableName = "votingEvent")
data class VotingEventCrossRef(
    val votingId: Long,
    @ColumnInfo(index = true) val eventId: Long
)
