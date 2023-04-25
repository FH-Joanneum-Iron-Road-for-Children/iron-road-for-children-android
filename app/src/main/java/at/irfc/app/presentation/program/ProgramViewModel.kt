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

    val categoryList: StateFlow<List<EventCategory>> = repository.getCategories()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _selectedCategory: MutableStateFlow<EventCategory?> = MutableStateFlow(null)
    val selectedCategory: StateFlow<EventCategory?> = _selectedCategory

    private var loadEventsJob: Job? = null

    init {
        loadEvents()
    }

    fun loadEvents(force: Boolean = false) {
        loadEventsJob?.cancel()
        loadEventsJob = repository.loadEvents(force)
            .combine(selectedCategory) { events, categoryFilter ->
                events.applyCategoryFilter(categoryFilter)
            }
            .onEach { _eventListResource.value = it }
            .launchIn(viewModelScope)
    }

    fun toggleCategory(category: EventCategory) {
        _selectedCategory.update { current ->
            if (current != category) category else null
        }
    }

    private fun Resource<List<EventWithDetails>>.applyCategoryFilter(category: EventCategory?):
        Resource<List<EventWithDetails>> {
        fun List<EventWithDetails>.filterCategory() = this.filter { it.category == category }

        if (category == null) return this
        return when (this) {
            is Resource.Error -> this.copy(data = this.data?.filterCategory())
            is Resource.Loading -> this.copy(data = this.data?.filterCategory())
            is Resource.Success -> this.copy(data = this.data.filterCategory())
        }
    }
}
