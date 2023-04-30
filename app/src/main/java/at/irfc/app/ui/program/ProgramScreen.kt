package at.irfc.app.ui.program

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.irfc.app.data.local.entity.EventCategory
import at.irfc.app.data.local.entity.EventWithDetails
import at.irfc.app.generated.navigation.destinations.ProgramDetailScreenDestination
import at.irfc.app.presentation.program.ProgramViewModel
import at.irfc.app.util.Resource
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.navigate
import java.text.SimpleDateFormat
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

// Material 3 does not include a PullToRefresh right now // TODO replace when added
    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(eventListResource is Resource.Loading),
        onRefresh = { viewModel.loadEvents(force = true) }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(10.dp)
        ) {
            stickyHeader {
                ProgramListHeader(
                    eventListResource = eventListResource,
                    selectedCategory = selectedCategory,
                    categories = categories,
                    onToggleCategory = viewModel::toggleCategory
                )
            }

            eventListResource.data?.let { eventList ->
                items(eventList, EventWithDetails::id) { event ->
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        ),
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(
                                        ProgramDetailScreenDestination(event.id)
                                    )
                                }
                                .fillMaxWidth()
                        ) {
// Box for the image
                            Box(
                                modifier = Modifier
                                    .width(150.dp)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.White)
                            ) {
                                AsyncImage(
                                    model = event.image.path,
                                    contentDescription = event.image.title,
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(20.dp))

                                )
                            }

// Text on the right side of the image
                            Column(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        top = 30.dp,
                                        end = 30.dp,
                                        bottom = 30.dp
                                    )
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = event.title,
                                    style = MaterialTheme.typography.bodyLarge,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = "${SimpleDateFormat("HH:mm").format(event.startDate)}" +
                                        " - " + SimpleDateFormat(
                                            "HH:mm"
                                        ).format(event.endDate),
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Text(
                                    text = "Stage: ${event.location.name}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
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
    eventListResource: Resource<List<EventWithDetails>>,
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
        modifier = Modifier.fillMaxWidth(),
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
