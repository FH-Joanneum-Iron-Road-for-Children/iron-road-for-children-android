package at.irfc.app.presentation.program

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.irfc.app.data.local.entity.EventCategory
import at.irfc.app.data.local.entity.relations.EventWithDetails
import at.irfc.app.data.repository.EventRepository
import at.irfc.app.util.Resource
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ProgramViewModel(
    private val repository: EventRepository
) : ViewModel() {

    private val _eventListResource: MutableStateFlow<Resource<List<EventsOnDate>>> =
        MutableStateFlow(Resource.Loading())
    val eventListResource: StateFlow<Resource<List<EventsOnDate>>> = _eventListResource

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
                events.filterAndTransform(categoryFilter)
            }
            .onEach { _eventListResource.value = it }
            .launchIn(viewModelScope)
    }

    fun toggleCategory(category: EventCategory) {
        _selectedCategory.update { current ->
            if (current != category) category else null
        }
    }

    private fun Resource<List<EventWithDetails>>.filterAndTransform(category: EventCategory?):
        Resource<List<EventsOnDate>> {
        fun List<EventWithDetails>.filterCategory() =
            if (category == null) this else this.filter { it.category == category }

        fun List<EventWithDetails>.associateByDate(): List<EventsOnDate> {
            val days: Set<LocalDate> = this.mapTo(mutableSetOf()) { it.startDateTime.toLocalDate() }

            return days.fold(emptyList<EventsOnDate>()) { acc, date ->
                val dateEvents = this.filter {
                    date in it.startDateTime.toLocalDate()..it.endDateTime.toLocalDate()
                }

                acc.plus(
                    EventsOnDate(
                        date = date,
                        events = dateEvents.filterCategory().sortedBy(
                            EventWithDetails::startDateTime
                        )
                    )
                )
            }.sortedBy(EventsOnDate::date)
        }

        return when (this) {
            is Resource.Error -> Resource.Error(
                this.errorMessage,
                this.data?.associateByDate()
            )

            is Resource.Loading -> Resource.Loading(this.data?.associateByDate())
            is Resource.Success -> Resource.Success(this.data.associateByDate())
        }
    }
}

class EventsOnDate(val date: LocalDate, val events: List<EventWithDetails>) {
    val dayString: String = formatter.format(date)

    companion object {
        private val formatter = DateTimeFormatter.ofPattern("EEEE", Locale.GERMAN)
    }
}
