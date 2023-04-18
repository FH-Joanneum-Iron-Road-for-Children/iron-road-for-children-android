package at.irfc.app.ui.program.details

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.irfc.app.data.remote.api.mock.EventApiMock
import at.irfc.app.data.remote.dto.toEventEntity
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun ProgramDetailScreen(id: Long) {
    // TODO add VM and load event for id
    EventDetailsCard(
        modifier = Modifier.padding(15.dp),
        event = EventApiMock().events.first { it.eventId == id }.toEventEntity()
    )
}
