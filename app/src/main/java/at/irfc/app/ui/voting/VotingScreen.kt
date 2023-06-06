package at.irfc.app.ui.voting

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import at.irfc.app.presentation.program.VotingViewModel
import at.irfc.app.ui.core.DotsIndicator
import at.irfc.app.util.Resource
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalFoundationApi::class)
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

    Column {
        val votingsWithEvents = votingListResource.data
        val votingsPager = rememberPagerState()

        if (!votingsWithEvents.isNullOrEmpty()) {
            VotingsTabRow(pagerState = votingsPager, votingsWithEvents = votingsWithEvents)
        }

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
                        Text(stringResource(id = R.string.voting_refresh))
                    }
                }
            } else {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    VotingsPager(
                        pagerState = votingsPager,
                        votingsWithEvents = votingsWithEvents,
                        onVote = viewModel::submitVoting
                    )

                    if (BuildConfig.DEBUG) {
                        // Allow to clear the votings from the UI for easier testing/debugging
                        // (shown only in debug builds)
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
private fun VotingsTabRow(pagerState: PagerState, votingsWithEvents: List<VotingWithEvents>) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        val coroutineScope = rememberCoroutineScope()
        votingsWithEvents.forEachIndexed { index, votingWithEvents ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = { coroutineScope.launch { pagerState.scrollToPage(index) } },
                text = {
                    Text(votingWithEvents.voting.title)
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VotingsPager(
    pagerState: PagerState,
    votingsWithEvents: List<VotingWithEvents>,
    onVote: (voting: Voting, event: Event) -> Unit
) {
    HorizontalPager(
        pageCount = votingsWithEvents.size,
        state = pagerState,
        userScrollEnabled = false
    ) { page ->
        val voting = votingsWithEvents[page]
        val eventsForVoting = voting.events

        if (eventsForVoting.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
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
                    pageCount = eventsForVoting.size,
                    pageSpacing = 20.dp,
                    contentPadding = PaddingValues(
                        horizontal = 40.dp,
                        vertical = 20.dp
                    )
                ) { votingPage ->
                    val event = eventsForVoting[votingPage]
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
                    totalDots = eventsForVoting.size,
                    selectedIndex = eventsPager.currentPage,
                    dotSize = 8.dp
                )
            }
        }
    }
}
