package at.irfc.app.presentation.program

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.irfc.app.data.local.entity.EventCategory
import at.irfc.app.data.local.entity.EventWithDetails
import at.irfc.app.data.repository.EventRepository
import at.irfc.app.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

class ProgramViewModel(
    private val repository: EventRepository
) : ViewModel() {

    private val _eventListResource: MutableStateFlow<Resource<List<EventWithDetails>>> =
        MutableStateFlow(Resource.Loading())
    val eventListResource: StateFlow<Resource<List<EventWithDetails>>> = _eventListResource

    val categoryList: StateFlow<List<EventCategory>> = repository.getCategories().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        emptyList()
    )

    private val _selectedCategory: MutableStateFlow<EventCategory?> = MutableStateFlow(null)
    val selectedCategory: StateFlow<EventCategory?> = _selectedCategory

    private var loadEventsJob: Job? = null

    init {
        loadEvents()
    }

    fun loadEvents(force: Boolean = false) {
        loadEventsJob?.cancel()
        loadEventsJob = combine(
            repository.loadEvents(force),
            selectedCategory
        ) { events, selectedCategory ->
            when (events) {
                is Resource.Error -> events
                is Resource.Loading -> events
                is Resource.Success -> if (selectedCategory == null) {
                    events
                } else {
                    Resource.Success(
                        events.data.filter {
                            it.category == selectedCategory
                        }
                    )
                }
            }
        }
            .onEach { _eventListResource.value = it }
            .launchIn(viewModelScope)
    }

    fun toggleCategory(category: EventCategory) {
        _selectedCategory.update { current ->
            if (current != category) category else null
        }
    }
}
