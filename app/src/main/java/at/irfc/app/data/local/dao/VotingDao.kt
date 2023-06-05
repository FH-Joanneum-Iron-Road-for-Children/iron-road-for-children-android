package at.irfc.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import at.irfc.app.data.local.IrfcDatabase
import at.irfc.app.data.local.entity.ServerVoting
import at.irfc.app.data.local.entity.ServerVotingWithEvents
import at.irfc.app.data.local.entity.UserVoting
import at.irfc.app.data.local.entity.Voting
import at.irfc.app.data.local.entity.relations.VotingEventCrossRef
import at.irfc.app.data.local.entity.relations.VotingWithEvents
import kotlinx.coroutines.flow.Flow

@Dao
abstract class VotingDao(private val database: IrfcDatabase) {

    private val eventDao get() = database.eventDao()

    @Transaction
    @Query("SELECT * FROM votings")
    abstract fun getAll(): Flow<List<VotingWithEvents>>

    @Upsert(entity = Voting::class)
    protected abstract suspend fun upsertVotings(voting: List<ServerVoting>)

    @Query("DELETE FROM votings WHERE votingId NOT IN (:idsToKeep)")
    protected abstract suspend fun deleteNotInList(idsToKeep: Set<Long>)

    @Query("DELETE FROM votings WHERE votingId = :id")
    abstract suspend fun deleteById(id: Long)

    @Upsert
    protected abstract suspend fun insertVotingEvents(votingEvents: List<VotingEventCrossRef>)

    @Update(entity = Voting::class)
    abstract suspend fun insertUserVoting(voting: UserVoting)

    @Query("UPDATE votings SET votedEventId = NULL")
    abstract suspend fun clearUserVotings()

    @Transaction
    open suspend fun replaceVotings(votingsWithEvents: List<ServerVotingWithEvents>) {
        upsertVotings(votingsWithEvents.map(ServerVotingWithEvents::voting))
        deleteNotInList(votingsWithEvents.mapTo(mutableSetOf()) { it.voting.id })
        eventDao.upsertEvents(votingsWithEvents.flatMap { it.events })
        insertVotingEvents(
            votingsWithEvents.flatMap { votingWithEvents ->
                votingWithEvents.events.map { event ->
                    VotingEventCrossRef(votingId = votingWithEvents.voting.id, eventId = event.id)
                }
            }
        )
    }
}
