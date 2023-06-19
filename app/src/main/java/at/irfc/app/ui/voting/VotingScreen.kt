package at.irfc.app.ui.voting

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import at.irfc.app.BuildConfig
import at.irfc.app.R
import at.irfc.app.data.local.entity.Event
import at.irfc.app.data.local.entity.Voting
import at.irfc.app.data.local.entity.relations.VotingWithEvents
import at.irfc.app.presentation.voting.VotingViewModel
import at.irfc.app.ui.core.DotsIndicator
import at.irfc.app.util.Resource
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.getViewModel

@Composable
@Destination
fun VotingScreen(
    viewModel: VotingViewModel = getViewModel()
) {
    val votingListResource = viewModel.votingListResource.collectAsState().value

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.toastFlow.collect {
            Toast.makeText(context, it.getMessage(context), Toast.LENGTH_LONG).show()
        }
    }

    var confirmVotingFor by remember { mutableStateOf<VoteEventRequest?>(null) }
    if (confirmVotingFor != null) {
        ConfirmVotingDialog(
            votingEventName = confirmVotingFor!!.event.title,
            onCancel = { confirmVotingFor = null },
            onConfirm = {
                viewModel.submitVoting(confirmVotingFor!!.voting, confirmVotingFor!!.event)
                confirmVotingFor = null
            }
        )
    }

    Column {
        val votingsWithEvents = votingListResource.data

        VotingHeader(votingListResource = votingListResource)

        // Material 3 does not include a PullToRefresh right now
        // TODO replace when added
        SwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            state = rememberSwipeRefreshState(votingListResource is Resource.Loading),
            onRefresh = { viewModel.loadVotings(force = true) }
        ) {
            if (votingsWithEvents.isNullOrEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = stringResource(id = R.string.voting_noActiveVotings),
                        textAlign = TextAlign.Center
                    )
                    Button(onClick = { viewModel.loadVotings(force = true) }) {
                        Text(stringResource(id = R.string.refresh))
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    itemsIndexed(
                        votingsWithEvents,
                        key = { _, voting -> voting.id }
                    ) { index, votingWithEvents ->
                        Voting(
                            voting = votingWithEvents,
                            onVote = { voting, event ->
                                confirmVotingFor = VoteEventRequest(voting, event)
                            }
                        )
                        if (votingsWithEvents.lastIndex != index) {
                            Divider()
                        }
                    }

                    if (BuildConfig.DEBUG) {
                        item("clearVotingsDev") {
                            // Allow to clear the votings from the UI for easier testing/debugging
                            // (shown only in debug builds,
                            //      sending the voting does only work once per build)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(
                                    onClick = {
                                        viewModel.clearVotings()
                                    }
                                ) {
                                    Text("Clear voting (dev build only)")
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
private fun VotingHeader(
    votingListResource: Resource<List<VotingWithEvents>>
) {
    if (votingListResource is Resource.Error) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.errorContainer)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = votingListResource.errorMessage.getMessage(),
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Voting(
    voting: VotingWithEvents,
    onVote: (voting: Voting, event: Event) -> Unit
) {
    Column(modifier = Modifier.padding(bottom = 15.dp)) {
        Column(
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            // TODO also add a crown here when voting is already decided?
            Text(text = voting.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Description for the Voting") // TODO show description for voting
        }

        if (voting.events.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.voting_noEventsToVote),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val eventsPager = rememberPagerState()
                HorizontalPager(
                    state = eventsPager,
                    pageCount = voting.events.size,
                    pageSpacing = 20.dp,
                    contentPadding = PaddingValues(
                        horizontal = 40.dp,
                        vertical = 20.dp
                    )
                ) { votingPage ->
                    val event = voting.events[votingPage]
                    if (event == voting.votedEvent) {
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            VotingCard(
                                modifier = Modifier.padding(10.dp),
                                voting = voting.voting,
                                event = event,
                                onVote = { onVote(voting.voting, event) }
                            )
                        }
                    } else {
                        VotingCard(
                            modifier = Modifier.padding(10.dp),
                            voting = voting.voting,
                            event = event,
                            onVote = { onVote(voting.voting, event) }
                        )
                    }
                }

                DotsIndicator(
                    totalDots = voting.events.size,
                    selectedIndex = eventsPager.currentPage,
                    dotSize = 8.dp
                )
            }
        }
    }
}

data class VoteEventRequest(val voting: Voting, val event: Event)
