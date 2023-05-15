package at.irfc.app.presentation.program

import androidx.annotation.StringRes
import at.irfc.app.data.local.entity.Event

data class EventListState(
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
)
