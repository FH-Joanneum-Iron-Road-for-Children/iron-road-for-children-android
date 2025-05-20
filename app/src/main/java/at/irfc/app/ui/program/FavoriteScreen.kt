package at.irfc.app.ui.program

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.irfc.app.presentation.program.EventsOnDate
import at.irfc.app.presentation.program.ProgramViewModel
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
@Destination
fun FavoriteScreen(
    navController: NavController,
    viewModel: ProgramViewModel = getViewModel()
) {
    val favorites by viewModel.favoriteEvents.collectAsState()
    val eventListResource = viewModel.eventListResource.collectAsState().value
    val pager = rememberPagerState(
        initialPage = 0,
        pageCount = { eventListResource.data?.size ?: 0 }
    )
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Favoriten") },
            navigationIcon = {} // Elimină săgeata de back
        )

        EventTabRowFavorites(
            pagerState = pager,
            eventOnDayList = eventListResource.data
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            FilterChip(
                text = "Programm",
                selected = false,
                onClick = {
                    navController.popBackStack()
                }
            )
        }

        // PAGINARE: afișează favoritele doar pentru ziua curentă
        HorizontalPager(
            state = pager
        ) { page ->
            val eventDay = eventListResource.data?.get(page)

            val dailyFavorites = favorites.filter { event ->
                event.date == eventDay?.date // asigură-te că `event.date` există și e comparabil
            }

            if (dailyFavorites.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Keine Favoriten für diesen Tag.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(dailyFavorites) { event ->
                        EventListItem(
                            event = event,
                            onEventClick = { /* navigare */ },
                            onFavoriteToggle = {
                                coroutineScope.launch {
                                    viewModel.toggleFavorite(it)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventTabRowFavorites(
    pagerState: PagerState,
    eventOnDayList: List<EventsOnDate>?
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        val coroutineScope = rememberCoroutineScope()
        eventOnDayList?.forEachIndexed { index, events ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Column {
                        Text(
                            text = events.dayString,
                            maxLines = 1
                        )
                        Text(
                            text = events.dateString,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1
                        )
                    }
                }
            )
        }
    }
}
