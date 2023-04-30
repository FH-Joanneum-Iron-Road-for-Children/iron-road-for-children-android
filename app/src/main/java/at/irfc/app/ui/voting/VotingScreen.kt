package at.irfc.app.ui.voting

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import at.irfc.app.R
import at.irfc.app.data.local.entity.EventWithDetails
import at.irfc.app.presentation.program.ProgramViewModel
import at.irfc.app.ui.program.ProgramListHeader
import at.irfc.app.util.Resource
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
@Destination
fun VotingScreen(
    viewModel: ProgramViewModel = getViewModel()
) {
    val eventListResource = viewModel.eventListResource.collectAsState().value
    // TODO replace the hardcoded dates
    val startDate = LocalDate.now().plusDays(-1).atStartOfDay()
    val endDate = LocalDate.now().plusDays(+1).atStartOfDay()
    // Material 3 does not include a PullToRefresh right now
    // TODO replace when added
    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(eventListResource is Resource.Loading),
        onRefresh = { viewModel.loadEvents(force = true) }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(10.dp)
        ) {
            stickyHeader {
                Text(
                    text = stringResource(id = R.string.header_VotingScreen),
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                )
                Text(
                    text = stringResource(id = R.string.subHeader_VotingScreen),
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                )
                ProgramListHeader(
                    eventListResource = eventListResource
                )
                // Voting not started yet
                if (isVotingStarted(startDate, endDate) == 0) {
                    countdown(startDate)
                }
                // Voting endet already
                if (isVotingStarted(startDate, endDate) == 2) {
                    // TODO give a filter to the voting list
                }
            }
            eventListResource.data?.let { eventList ->
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(eventList, EventWithDetails::id) { event ->
                            // TODO here is the db logic missing
                            if (isVotingStarted(startDate, endDate) == 2) {
                                // TODO give a filter to the voting list and
                                // set is selected to true
                            }
                            var isSelected by remember {
                                mutableStateOf(false)
                            } // declare inside of items
                            Box(
                                modifier = Modifier
                                    .width(250.dp)
                                    .height(350.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(
                                        width = if (isSelected) 4.dp else 0.dp,
                                        color = if (isSelected) Color.Yellow else Color.Black,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .background(Color(0xFFFFF9C4)) // TODO Check the color
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color(0xFFFFF9C4)),
                                    // TODO check why background not yellow line above
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Column {
                                        Image(
                                            painter = rememberImagePainter(
                                                data = event.image.path
                                            ),
                                            contentDescription = event.image.title,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .weight(0.5f)
                                                .height(150.dp)
                                                .clip(
                                                    RoundedCornerShape(
                                                        topStart = 8.dp,
                                                        topEnd = 8.dp
                                                    )
                                                )
                                        )
                                        Column(modifier = Modifier.weight(0.5f)) {
                                            Text(
                                                text = event.title,
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.padding(
                                                    horizontal = 16.dp,
                                                    vertical = 8.dp
                                                )
                                            )
                                            Text(
                                                text = event.description,
                                                style = MaterialTheme.typography.bodyMedium,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.padding(
                                                    horizontal = 16.dp,
                                                    vertical = 8.dp
                                                )
                                            )
                                        }
                                        if (isVotingStarted(startDate, endDate) == 1) {
                                            Button(
                                                onClick = {
                                                    isSelected = !isSelected
                                                    // TODO Send the data to the db if already voted
                                                    //  counter-1 and add the vote to the new band
                                                }, // update the value on button click
                                                modifier = Modifier
                                                    .padding(vertical = 8.dp)
                                                    .align(Alignment.CenterHorizontally)
                                            ) {
                                                Text(
                                                    text = stringResource(id = R.string.button_vote)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun ProgramListHeader(
    eventListResource: Resource<List<EventWithDetails>>
) {
    if (eventListResource is Resource.Error) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.errorContainer)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = eventListResource.errorMessage),
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun isVotingStarted(startOfVoting: LocalDateTime, endOfVoting: LocalDateTime): Int {
    val currentDateTime = LocalDateTime.now()
    val remainingToStart = Duration.between(currentDateTime, startOfVoting).toMillis()
    val remainingToEnd = Duration.between(currentDateTime, endOfVoting).toMillis()

    // Voting did not start yet start countdown to start
    if (remainingToStart > 0) {
        return 0
    }
    // Voting is ongoing
    if (remainingToEnd > 0) {
        return 1
    } else { // Voting ended show winner
        return 2
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun countdown(targetDate: LocalDateTime) {
    val currentDateTime = LocalDateTime.now()
    val remaining = Duration.between(currentDateTime, targetDate).toMillis()
    val days = TimeUnit.MILLISECONDS.toDays(remaining)
    val hours = TimeUnit.MILLISECONDS.toHours(remaining) % 24
    val minutes = TimeUnit.MILLISECONDS.toMinutes(remaining) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(remaining) % 60

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp),
            text = stringResource(id = R.string.text_start),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold

        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp),
            text = "$days " + stringResource(id = R.string.header_days),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold

        )
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp),
            text = "  $hours " + stringResource(id = R.string.header_hours),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold

        )
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp),
            text = "  %02d ".format(minutes) + stringResource(id = R.string.header_minutes),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold

        )
    }
}
