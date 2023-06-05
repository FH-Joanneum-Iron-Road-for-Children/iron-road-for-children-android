package at.irfc.app.presentation.program

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.irfc.app.data.local.entity.relations.EventWithDetails
import at.irfc.app.data.repository.EventRepository
import at.irfc.app.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProgramDetailViewModel(
    id: Long,
    private val repository: EventRepository
) : ViewModel() {

    private val _eventResource: MutableStateFlow<Resource<EventWithDetails?>> =
        MutableStateFlow(Resource.Loading())
    val eventResource: StateFlow<Resource<EventWithDetails?>> = _eventResource

    private var loadEventsJob: Job? = null

    init {
        loadEvents(id = id)
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
