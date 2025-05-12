package at.irfc.app.ui.program

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.irfc.app.presentation.program.ProgramViewModel
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun FavoriteScreen(
    viewModel: ProgramViewModel = getViewModel()
) {
    val favorites by viewModel.favoriteEvents.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Favorite Events") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (favorites.isEmpty()) {
                item {
                    Text(
                        text = "Es gibt noch keine Favoriten.",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(26.dp)
                    )
                }
            } else {
                items(favorites) { event ->
                    EventListItem(
                        event = event,
                        onEventClick = { /* navigare la detalii, dacă vrei */ },
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
