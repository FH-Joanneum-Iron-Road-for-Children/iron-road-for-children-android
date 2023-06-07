package at.irfc.app.ui.voting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.irfc.app.R
import at.irfc.app.data.local.entity.Event
import at.irfc.app.data.local.entity.Voting
import at.irfc.app.ui.theme.IronRoadForChildrenTheme
import coil.compose.AsyncImage
import java.time.LocalDateTime

@Composable
fun VotingCard(
    modifier: Modifier = Modifier,
    voting: Voting,
    event: Event,
    onVote: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        val padding = Modifier.padding(
            horizontal = 16.dp,
            vertical = 10.dp
        )

        Text(
            text = event.title,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = padding
        )

        AsyncImage(
            model = event.image.path,
            contentDescription = event.image.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2.5f),
            error = rememberVectorPainter(image = Icons.Outlined.BrokenImage)
        )

        Text(
            text = event.description,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            modifier = padding
        )

        if (!voting.voted) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = onVote
                ) {
                    Text(stringResource(id = R.string.voting_sendVote))
                }
            }
        } else {
            Spacer(modifier = modifier.height(20.dp))
        }
    }
}

@Composable
@Preview
private fun VotingCardPreview() {
    IronRoadForChildrenTheme {
        VotingCard(
            voting = Voting(
                id = 1,
                title = "Test Voting",
                isActive = true,
                votedEventId = null,
                updated = LocalDateTime.now()
            ),
            event = Event(
                id = 1,
                title = "Test Event",
                startDateTime = LocalDateTime.now().minusHours(1),
                endDateTime = LocalDateTime.now().plusHours(1),
                description = "Test description of the event.",
                categoryId = 1,
                locationId = 1,
                image = Event.Image(
                    title = "Header picture",
                    path = "https://picsum.photos/350/100"
                ),
                updated = LocalDateTime.now()
            ),
            onVote = {}
        )
    }
}
