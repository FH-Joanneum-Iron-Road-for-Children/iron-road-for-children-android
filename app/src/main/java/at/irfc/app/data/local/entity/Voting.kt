package at.irfc.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import at.irfc.app.data.local.entity.relations.EventWithDetails
import java.time.LocalDateTime

@Entity(tableName = "votings")
data class Voting(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "votingId")
    val id: Long,

    val title: String,
    val isActive: Boolean,
    @ColumnInfo(defaultValue = "NULL")
    val votedEventId: Long? = null,

    val updated: LocalDateTime
) {
    inline val voted get() = votedEventId != null
}

data class UserVoting(
    val votingId: Long,
    val votedEventId: Long
)

data class ServerVoting(
    @ColumnInfo(name = "votingId") val id: Long,
    val title: String,
    val isActive: Boolean,
    val updated: LocalDateTime = LocalDateTime.now()
)

data class ServerVotingWithEvents(
    val voting: ServerVoting,
    val events: List<EventWithDetails>
)
