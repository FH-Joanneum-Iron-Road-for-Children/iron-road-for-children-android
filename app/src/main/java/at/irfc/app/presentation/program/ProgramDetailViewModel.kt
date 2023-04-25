package at.irfc.app.presentation.program

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.irfc.app.data.local.entity.EventWithDetails
import at.irfc.app.data.repository.EventRepository
import at.irfc.app.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProgramDetailViewModel(
    private val repository: EventRepository
) : ViewModel() {

    private val _eventResource: MutableState<Resource<EventWithDetails>> =
        mutableStateOf(Resource.Loading())
    val eventResource: State<Resource<EventWithDetails>> = _eventResource

    private var loadEventsJob: Job? = null

    init {
        loadEvents(id = 1)
    }

    fun loadEvents(force: Boolean = false, id: Long) {
        loadEventsJob?.cancel()
        loadEventsJob = repository
            .loadEvent(force, id)
            .onEach { event ->
                _eventResource.value = event
            }
            .launchIn(viewModelScope)
    }
}
