package at.irfc.app.data.remote.api.mock

import android.util.Log
import at.irfc.app.data.remote.api.VotingApi
import at.irfc.app.data.remote.dto.UserVotingDto
import at.irfc.app.data.remote.dto.VotingDto
import kotlinx.coroutines.delay

class VotingApiMock(eventApiMock: EventApiMock) : VotingApi {
    private val votings = listOf(
        VotingDto(
            votingId = 1,
            title = "NovaRock Band",
            isActive = true,
            events = eventApiMock.events.filter { it.eventCategory.id == 1L }
        )
    )

    override suspend fun getVotings(): List<VotingDto> = votings

    override suspend fun getVoting(id: Long): VotingDto? = votings.find { it.votingId == id }

    override suspend fun submitUserVoting(voting: UserVotingDto) {
        Log.d(
            this::class.simpleName,
            "Submitted voting for voting: ${voting.votingId}. Voted for event: ${voting.eventId}."
        )
        delay(500)
    }
}
