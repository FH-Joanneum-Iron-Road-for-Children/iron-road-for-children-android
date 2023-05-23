package at.irfc.app.ui.program

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.irfc.app.data.local.entity.EventCategory
import at.irfc.app.data.local.entity.relations.EventWithDetails
import at.irfc.app.generated.navigation.destinations.ProgramDetailScreenDestination
import at.irfc.app.presentation.program.EventsOnDate
import at.irfc.app.presentation.program.ProgramViewModel
import at.irfc.app.util.Resource
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.navigate
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Destination
@RootNavGraph(start = true)
fun ProgramScreen(
    navController: NavController,
    viewModel: ProgramViewModel = getViewModel()
) {
    val eventListResource = viewModel.eventListResource.collectAsState().value
    val selectedCategory = viewModel.selectedCategory.collectAsState().value
    val categories = viewModel.categoryList.collectAsState().value.filter { it != selectedCategory }

    Column {
        val pager = rememberPagerState()
        EventListTabRow(pagerState = pager, eventOnDayList = eventListResource.data)
        ProgramListHeader(
            eventListResource = eventListResource,
            selectedCategory = selectedCategory,
            categories = categories,
            onToggleCategory = viewModel::toggleCategory
        )
        // Material 3 does not include a PullToRefresh right now // TODO replace when added
        SwipeRefresh(
            state = rememberSwipeRefreshState(eventListResource is Resource.Loading),
            onRefresh = { viewModel.loadEvents(force = true) }
        ) {
            EventListPager(
                pagerState = pager,
                eventOnDayList = eventListResource.data,
                onEventClick = { event ->
                    navController.navigate(
                        ProgramDetailScreenDestination(event.id)
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EventListPager(
    pagerState: PagerState,
    eventOnDayList: List<EventsOnDate>?,
    onEventClick: (EventWithDetails) -> Unit
) {
    HorizontalPager(
        pageCount = eventOnDayList?.size ?: 1,
        state = pagerState
    ) { page ->
        val eventsForPage = eventOnDayList?.getOrNull(page)?.events
        if (eventsForPage.isNullOrEmpty()) {
            // TODO
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(eventsForPage, EventWithDetails::id) { event ->
                    Text(
                        text = event.title,
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable { onEventClick(event) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EventListTabRow(pagerState: PagerState, eventOnDayList: List<EventsOnDate>?) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        val coroutineScope = rememberCoroutineScope()
        eventOnDayList?.forEachIndexed { index, events ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                text = {
                    Text(events.dayString)
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun ProgramListHeader(
    eventListResource: Resource<*>,
    selectedCategory: EventCategory?,
    categories: List<EventCategory>,
    onToggleCategory: (EventCategory) -> Unit
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

    LazyRow(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (selectedCategory != null) {
            item(selectedCategory.id) {
                FilterChip(
                    modifier = Modifier.animateItemPlacement(),
                    text = selectedCategory.name,
                    selected = true,
                    onClick = { onToggleCategory(selectedCategory) }
                )
            }
        }
        items(categories, EventCategory::id) {
            FilterChip(
                modifier = Modifier.animateItemPlacement(),
                text = it.name,
                selected = false,
                onClick = { onToggleCategory(it) }
            )
        }
    }
}
