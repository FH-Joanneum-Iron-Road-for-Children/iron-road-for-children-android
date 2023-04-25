package at.irfc.app.ui.program.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import at.irfc.app.R
import at.irfc.app.data.local.entity.EventPicture
import at.irfc.app.data.local.entity.EventWithDetails
import coil.compose.AsyncImage
import java.text.SimpleDateFormat

@Composable
fun EventDetailsCard(
    modifier: Modifier = Modifier,
    event: EventWithDetails
) {
    val contentPadding = 16.dp
    val scrollState = rememberScrollState()
    Card(
        modifier = modifier
            .fillMaxWidth()
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
                    .aspectRatio(2.5f)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding)
            ) {
                Text(
                    text = stringResource(id = R.string.programDetailScreen_TimeHeading),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${SimpleDateFormat("HH:mm").format(event.startDate)} - " +
                        SimpleDateFormat("HH:mm").format(event.endDate)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = event.location.name)
                Text(text = event.category.name)

                Spacer(modifier = Modifier.height(10.dp))

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
                            modifier = Modifier.aspectRatio(4 / 3f)
                        )
                    }
                }
            }
        }
    }
}
