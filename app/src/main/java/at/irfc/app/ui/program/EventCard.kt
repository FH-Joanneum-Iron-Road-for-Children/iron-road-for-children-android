package at.irfc.app.ui.program

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import at.irfc.app.data.local.entity.EventWithDetails
import coil.compose.AsyncImage
import java.time.format.DateTimeFormatter

@Composable
fun EventCard(event: EventWithDetails, onEventClick: (EventWithDetails) -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier
            .clickable { onEventClick(event) }
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Provided image ratio should be 2,5:1 -> crop
            AsyncImage(
                model = event.image.path,
                contentDescription = event.image.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(0.2f)
                    .aspectRatio(1f)
            )

            Row(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = event.location.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                val timeString = remember(event.startDateTime, event.endDateTime) {
                    val formatter = DateTimeFormatter.ofPattern("HH:mm")
                    "${formatter.format(event.startDateTime)} - " +
                        formatter.format(event.endDateTime)
                }
                Text(
                    text = timeString,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
