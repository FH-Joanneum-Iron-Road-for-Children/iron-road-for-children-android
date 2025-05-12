package at.irfc.app.ui.program

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import at.irfc.app.data.local.entity.relations.EventWithDetails
import coil.compose.AsyncImage
import java.time.format.DateTimeFormatter

@Composable
fun EventListItem(
    event: EventWithDetails,
    onEventClick: (EventWithDetails) -> Unit,
    onFavoriteToggle: (EventWithDetails) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEventClick(event) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 🔹 Imagine + text
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = event.image.path,
                    contentDescription = event.image.title ?: "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(0.2f)
                        .aspectRatio(1f),
                    error = rememberVectorPainter(image = Icons.Outlined.BrokenImage)
                )

                Column(
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(start = 10.dp),
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
            }

            // 🔸 Ora + inimioară (în colțul din dreapta sus)
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                IconToggleButton(
                    checked = event.event.isFavorite,
                    onCheckedChange = { onFavoriteToggle(event) }
                ) {
                    val icon = if (event.event.isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    }

                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = androidx.compose.ui.graphics.Color.Red
                    )
                }

                val timeString = remember(event.startDateTime, event.endDateTime) {
                    val formatter = DateTimeFormatter.ofPattern("HH:mm")
                    "${formatter.format(event.startDateTime)} - ${formatter.format(
                        event.endDateTime
                    )}"
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
