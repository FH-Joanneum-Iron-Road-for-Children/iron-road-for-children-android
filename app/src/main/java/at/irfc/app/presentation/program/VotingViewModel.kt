package at.irfc.app.presentation.program

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.irfc.app.R
import at.irfc.app.data.local.entity.relations.VotingWithEvents
import at.irfc.app.data.repository.VotingRepository
import at.irfc.app.util.Resource
import at.irfc.app.util.StringResource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class VotingViewModel(
    private val repository: VotingRepository
) : ViewModel() {

    private val _votingListResource: MutableStateFlow<Resource<List<VotingWithEvents>>> =
        MutableStateFlow(Resource.Loading())
    val votingListResource: StateFlow<Resource<List<VotingWithEvents>>> = _votingListResource

    private val _toastFlow: MutableSharedFlow<StringResource> = MutableSharedFlow()
    val toastFlow: Flow<StringResource> = _toastFlow

    private var loadJob: Job? = null

    init {
        loadVotings()
    }

    fun loadVotings(force: Boolean = false) {
        loadJob?.cancel()
        loadJob = repository.loadActiveVotings(force)
            .onEach { _votingListResource.value = it }
            .launchIn(viewModelScope)
    }

    @Suppress("ReturnCount")
    fun submitVoting(votingId: Long, eventId: Long) {
        val voting = votingListResource.value.data?.find { it.id == votingId } ?: return
        val event = voting.events.find { it.id == eventId } ?: return

        // Ensure that the voting is active
        if (!voting.isActive) {
            viewModelScope.launch {
                _toastFlow.emit(StringResource(R.string.voting_failedNotActive))
            }
            return
        }

        // It is only allowed to vote once for one specific voting
        if (voting.voted) {
            viewModelScope.launch {
                _toastFlow.emit(StringResource(R.string.voting_alreadyVoted))
            }
            return
        }

        viewModelScope.launch {
            @Suppress("TooGenericExceptionCaught", "SwallowedException")
            try {
                repository.submitVoting(votingId = votingId, eventId = eventId)
                viewModelScope.launch {
                    _toastFlow.emit(StringResource(R.string.voting_successfullyVoted, event.title))
                }
            } catch (e: Exception) {
                viewModelScope.launch {
                    _toastFlow.emit(StringResource(R.string.voting_submitVoteFailed))
                }
            }
        }
    }

    /**
     * Can only be used in DEBUG builds, will do nothing in production releases.
     * @see [VotingRepository.clearUserVotings]
     */
    fun clearVotings() {
        viewModelScope.launch {
            repository.clearUserVotings()
        }
    }
}
