package at.irfc.app.ui.program

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.irfc.app.R
import at.irfc.app.data.local.entity.EventCategory
import at.irfc.app.data.local.entity.EventWithDetails
import at.irfc.app.generated.navigation.destinations.ProgramDetailScreenDestination
import at.irfc.app.presentation.program.ProgramViewModel
import at.irfc.app.ui.theme.IrfcYellow
import at.irfc.app.util.Resource
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.navigate
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
        Column(modifier = Modifier.padding(10.dp)) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                if (selectedCategory != null) {
                    item {
                        FilterChip(selectedCategory.name, selected = true, onClick = {
                            viewModel.toggleCategory(selectedCategory)
                        })
                    }
                }
                items(categories, EventCategory::id) {
                    FilterChip(it.name, selected = false, onClick = {
                        viewModel.toggleCategory(it)
                    })
                }
            }
            LazyColumn {
                if (eventListResource is Resource.Error) {
                    stickyHeader {
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
                eventListResource.data?.let { eventList ->
                    items(eventList, EventWithDetails::id) { event ->
                        Text(
                            text = event.title,
                            modifier = Modifier
                                .padding(5.dp)
                                .clickable {
                                    navController.navigate(ProgramDetailScreenDestination(event.id))
                                }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
            AsyncImage(model = "https://picsum.photos/200", contentDescription = "Some picture")
        }
    }
}

@Composable
fun FilterChip(text: String, selected: Boolean, onClick: () -> Unit) {
    val backgroundColor by animateColorAsState(if (selected) IrfcYellow else Color.White)
    Button(
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = Color.Black
        ),
        onClick = onClick
    ) {
        if (selected) {
            Icon(
                Icons.Filled.Close,
                contentDescription = stringResource(id = R.string.programmScreen_clearFilter)
            )
        }
        Text(text)
    }
}
