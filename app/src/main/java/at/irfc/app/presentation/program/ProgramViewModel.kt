package at.irfc.app.presentation.program

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.irfc.app.data.local.entity.Event
import at.irfc.app.data.repository.EventRepository
import at.irfc.app.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProgramViewModel(
    private val repository: EventRepository,
) : ViewModel() {

    private val _eventListResource: MutableState<Resource<List<Event>>> =
        mutableStateOf(Resource.Loading())
    val eventListResource: State<Resource<List<Event>>> = _eventListResource

    private var loadEventsJob: Job? = null

    init {
        loadEvents()
    }

    fun loadEvents(force: Boolean = false) {
        loadEventsJob?.cancel()
        loadEventsJob = repository
            .loadEvents(force)
            .onEach { _eventListResource.value = it }
            .launchIn(viewModelScope)
    }
}