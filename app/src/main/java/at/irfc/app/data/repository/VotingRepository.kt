package at.irfc.app.data.repository

import at.irfc.app.data.local.dao.VotingDao
import at.irfc.app.data.local.entity.UserVoting
import at.irfc.app.data.local.entity.relations.VotingWithEvents
import at.irfc.app.data.remote.api.VotingApi
import at.irfc.app.data.remote.dto.UserVotingDto
import at.irfc.app.data.remote.dto.VotingDto
import at.irfc.app.data.remote.dto.toVotingEntity
import at.irfc.app.util.Resource
import at.irfc.app.util.cachedRemoteResource
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.hours
import kotlin.time.toJavaDuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VotingRepository(
    private val votingDao: VotingDao,
    private val votingApi: VotingApi
) {

    fun loadActiveVotings(force: Boolean): Flow<Resource<List<VotingWithEvents>>> {
        return cachedRemoteResource(
            query = { votingDao.getAll().map { it.filter(VotingWithEvents::isActive) } },
            fetch = votingApi::getVotings,
            update = { votingDao.replaceVotings(it.map(VotingDto::toVotingEntity)) },
            shouldFetch = { voting ->
                val updateWhenOlderThan = LocalDateTime.now() - cacheDuration
                force || voting.isEmpty() || voting.any { it.updated.isBefore(updateWhenOlderThan) }
            }
        )
    }

    suspend fun submitVoting(votingId: Long, eventId: Long) {
        votingApi.submitUserVoting(
            UserVotingDto(
                votingId = votingId,
                eventId = eventId,
                deviceId = "" // TODO
            )
        )
        votingDao.insertUserVoting(UserVoting(votingId = votingId, voted = true))
    }

    companion object {
        val cacheDuration = 2.hours.toJavaDuration()
    }
}
