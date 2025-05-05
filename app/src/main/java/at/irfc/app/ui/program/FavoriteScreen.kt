package at.irfc.app.ui.program

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.irfc.app.presentation.program.ProgramViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.getViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun FavoriteScreen(
    navController: NavController,
    viewModel: ProgramViewModel = getViewModel()
) {
    val favorites by viewModel.favoriteEvents.collectAsState()

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
            items(favorites) { event ->
                EventListItem(
                    event = event,
                    onEventClick = { /* poți naviga la detalii aici dacă vrei */ },
                    onFavoriteToggle = { viewModel.toggleFavorite(it) }
                )
            }
        }
    }
}
