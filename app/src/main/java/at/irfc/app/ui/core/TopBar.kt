package at.irfc.app.ui.core

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import at.irfc.app.R
import at.irfc.app.generated.navigation.NavGraphs
import at.irfc.app.generated.navigation.appCurrentDestinationAsState
import at.irfc.app.generated.navigation.destinations.AboutUsScreenDestination
import at.irfc.app.generated.navigation.destinations.GalleryScreenDestination
import at.irfc.app.generated.navigation.destinations.HomeScreenDestination
import at.irfc.app.generated.navigation.destinations.MapScreenDestination
import at.irfc.app.generated.navigation.destinations.ProgramDetailScreenDestination
import at.irfc.app.generated.navigation.destinations.ProgramScreenDestination
import at.irfc.app.generated.navigation.destinations.SpotifyPlayerScreenDestination
import at.irfc.app.generated.navigation.destinations.TypedDestination
import at.irfc.app.generated.navigation.destinations.VotingScreenDestination
import at.irfc.app.generated.navigation.startAppDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    val destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    @SuppressLint("RestrictedApi")
    val backStack = navController.currentBackStack.collectAsState().value
    if (destination == HomeScreenDestination) {
        return
    } else {
        TopAppBar(
            navigationIcon = {
                // There is also a entry for the RootNavigationGraph in the list
                if (backStack.count() > 2) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.NavigateBefore,
                            contentDescription = stringResource(R.string.nav_back)
                        )
                    }
                }
            },
            title = {
                Text(text = destination.screenTitle())
            }
        )
    }
}

@Composable
private fun TypedDestination<*>?.screenTitle(): String = when (this) {
    null -> "" // Empty when starting
    ProgramScreenDestination -> stringResource(id = R.string.nav_bar_program)
    VotingScreenDestination -> stringResource(id = R.string.nav_bar_voting)
    MapScreenDestination -> stringResource(id = R.string.nav_bar_map)
    AboutUsScreenDestination -> stringResource(id = R.string.header_aboutUs)
    ProgramDetailScreenDestination -> stringResource(id = R.string.header_programDetailScreen)
    GalleryScreenDestination -> stringResource(id = R.string.header_pictures)
    SpotifyPlayerScreenDestination -> stringResource(id = R.string.header_spotifyPlayer)
    else -> ""
}
