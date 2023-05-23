package at.irfc.app.presentation.program

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.irfc.app.data.local.entity.relations.VotingWithEvents
import at.irfc.app.data.repository.VotingRepository
import at.irfc.app.util.Resource
import kotlinx.coroutines.Job
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

    fun submitVoting(votingId: Long, eventId: Long) {
        // It is only allowed to vote once for one specific voting
        if (votingListResource.value.data?.find { it.id == votingId }?.voted == true) {
            // TODO toast already voted?
            return
        }
        viewModelScope.launch {
            try {
                repository.submitVoting(votingId = votingId, eventId = eventId)
            } catch (e: Exception) {
                // TODO toast that voting failed?
            }
        }
    }
}
