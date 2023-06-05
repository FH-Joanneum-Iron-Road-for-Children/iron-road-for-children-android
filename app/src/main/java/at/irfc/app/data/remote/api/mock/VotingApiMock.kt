package at.irfc.app.data.remote.api.mock

import android.util.Log
import at.irfc.app.data.remote.api.VotingApi
import at.irfc.app.data.remote.dto.UserVotingDto
import at.irfc.app.data.remote.dto.VotingDto
import kotlinx.coroutines.delay

class VotingApiMock : VotingApi {
    private val votings = listOf(
        VotingDto(
            votingId = 1,
            title = "NovaRock Band",
            isActive = true,
            events = EventApiMock.events.filter { it.eventCategory.id == 1L }
        )
        /*VotingDto(
            votingId = 2,
            title = "Tattoo",
            isActive = true,
            events = EventApiMock.events.filter { it.eventCategory.id == 2L }
        ),
        VotingDto(
            votingId = 3,
            title = "Inactive",
            isActive = false,
            events = EventApiMock.events.filter { it.eventCategory.id == 2L }
        ),
        VotingDto(
            votingId = 4,
            title = "No Events",
            isActive = true,
            events = emptyList()
        )*/
    )

    override suspend fun getVotings(): List<VotingDto> {
        delay(500) // Simulate some network latency
        return votings
    }

    override suspend fun getVoting(id: Long): VotingDto? = getVotings().find { it.votingId == id }

    override suspend fun submitUserVoting(voting: UserVotingDto) {
        Log.d(
            this::class.simpleName,
            "Submitted voting for voting: ${voting.votingId}. Voted for event: ${voting.eventId}."
        )
        delay(500) // Simulate some network latency
    }
}
