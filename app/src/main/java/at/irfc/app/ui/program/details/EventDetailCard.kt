package at.irfc.app.ui.program.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material.icons.outlined.Label
import androidx.compose.material.icons.outlined.PinDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import at.irfc.app.R
import at.irfc.app.data.local.entity.EventPicture
import at.irfc.app.data.local.entity.relations.EventWithDetails
import at.irfc.app.ui.core.IconText
import coil.compose.AsyncImage
import java.time.format.DateTimeFormatter

@Composable
fun EventDetailsCard(
    modifier: Modifier = Modifier,
    event: EventWithDetails
) {
    val contentPadding = 16.dp
    val scrollState = rememberScrollState()
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(contentPadding)
            )

            // Image ratio should be 2,5:1
            AsyncImage(
                model = event.image.path,
                contentDescription = event.image.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2.5f),
                error = rememberVectorPainter(image = Icons.Outlined.BrokenImage)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding)
            ) {
                val timeString = remember(event.startDateTime, event.endDateTime) {
                    val formatter = DateTimeFormatter.ofPattern("HH:mm")
                    "${formatter.format(event.startDateTime)} - ${
                        formatter.format(
                            event.endDateTime
                        )
                    }"
                }

                val dateString = remember(event.startDateTime) {
                    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    formatter.format(event.startDateTime)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = stringResource(id = R.string.programDetailScreen_TimeHeading),
                            fontWeight = FontWeight.Bold
                        )
                        IconText(
                            iconVector = Icons.Filled.Schedule,
                            iconDescription = stringResource(
                                id = R.string.programDetailScreen_TimeHeading
                            ),
                            text = timeString
                        )
                    }
                    Column {
                        Text(
                            text = stringResource(id = R.string.programDetailScreen_DateHeading),
                            fontWeight = FontWeight.Bold
                        )
                        IconText(
                            iconVector = Icons.Filled.Event,
                            iconDescription = stringResource(
                                id = R.string.programDetailScreen_DateHeading
                            ),
                            text = dateString
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                IconText(
                    iconVector = Icons.Outlined.PinDrop,
                    iconDescription = "Ort",
                    text = event.location.name
                )
                Spacer(modifier = Modifier.height(5.dp))
                IconText(
                    iconVector = Icons.Outlined.Label,
                    iconDescription = "Category",
                    text = event.category.name
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(text = event.description)
            }

            if (event.additionalImages.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(contentPadding)
                        .requiredHeight(100.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 20.dp,
                        alignment = Alignment.CenterHorizontally
                    )
                ) {
                    items(event.additionalImages, EventPicture::id) { image ->
                        // Image ratio should be 4:3
                        AsyncImage(
                            model = image.path,
                            contentDescription = image.title,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.aspectRatio(4 / 3f),
                            error = rememberVectorPainter(image = Icons.Outlined.BrokenImage)
                        )
                    }
                }
            }
        }
    }
}
