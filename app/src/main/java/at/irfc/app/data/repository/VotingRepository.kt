package at.irfc.app.data.repository

import android.content.Context
import android.provider.Settings
import android.util.Log
import at.irfc.app.BuildConfig
import at.irfc.app.data.local.dao.VotingDao
import at.irfc.app.data.local.entity.UserVoting
import at.irfc.app.data.local.entity.relations.VotingWithEvents
import at.irfc.app.data.remote.api.VotingApi
import at.irfc.app.data.remote.dto.UserVotingDto
import at.irfc.app.data.remote.dto.VotingDto
import at.irfc.app.data.remote.dto.toVotingEntity
import at.irfc.app.util.Resource
import at.irfc.app.util.cachedRemoteResource
import at.irfc.app.util.extensions.sha256
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.hours
import kotlin.time.toJavaDuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class VotingRepository(
    private val votingDao: VotingDao,
    private val votingApi: VotingApi
) : KoinComponent {

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
        val context = get<Context>()
        val deviceId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        // It should not be possible to associate multiple votes for different votings to one user
        val uniqueId = "android-${deviceId}___unique-device-id___$votingId".sha256()
        votingApi.submitUserVoting(
            UserVotingDto(
                votingId = votingId,
                eventId = eventId,
                deviceId = uniqueId
            )
        )
        votingDao.insertUserVoting(UserVoting(votingId = votingId, votedEventId = eventId))
    }

    /**
     * Can only be used in DEBUG builds, will do nothing in production releases.
     */
    suspend fun clearUserVotings() {
        // Clearing the votings is only allowed in DEBUG mode (for dev purpose, not for prod releases)
        if (BuildConfig.DEBUG) {
            votingDao.clearUserVotings()
            Log.d(
                /* tag = */ VotingRepository::class.simpleName,
                /* msg = */ "Cleared user votings. Will not be executed for production builds."
            )
        } else {
            Log.w(
                /* tag = */ VotingRepository::class.simpleName,
                /* msg = */ "Tried to clear user votings in production app"
            )
        }
    }

    companion object {
        val cacheDuration = 2.hours.toJavaDuration()
    }
}
