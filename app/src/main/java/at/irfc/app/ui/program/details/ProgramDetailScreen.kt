package at.irfc.app.ui.program.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.irfc.app.R
import at.irfc.app.presentation.program.ProgramDetailViewModel
import at.irfc.app.util.Resource
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Destination
fun ProgramDetailScreen(
    id: Long,
    navController: NavController,
    viewModel: ProgramDetailViewModel = getViewModel { parametersOf(id) }
) {
    val eventResource = viewModel.eventResource.collectAsState().value

    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(eventResource is Resource.Loading),
        onRefresh = { viewModel.loadEvents(force = true, id) }
    ) {
        Column {
            val event = eventResource.data
            if (eventResource is Resource.Error) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = eventResource.errorMessage.getMessage(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            if (event == null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.programDetailScreen_EventNotFound),
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = { navController.popBackStack() }) {
                        Text(text = stringResource(id = R.string.nav_back))
                    }
                }
            } else {
                EventDetailsCard(
                    modifier = Modifier.padding(15.dp),
                    event = event
                )
            }
        }
    }
}
