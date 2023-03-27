package at.irfc.app.ui.core

import androidx.compose.material.icons.Icons
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
import at.irfc.app.generated.navigation.destinations.MapScreenDestination
import at.irfc.app.generated.navigation.destinations.ProgramScreenDestination
import at.irfc.app.generated.navigation.destinations.TypedDestination
import at.irfc.app.generated.navigation.startAppDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    val destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination
    val backStack = navController.currentBackStack.collectAsState().value

    TopAppBar(
        navigationIcon = {
            // There is also a entry for the RootNavigationGraph in the list
            if (backStack.count() > 2) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Default.NavigateBefore,
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

@Composable
private fun TypedDestination<*>?.screenTitle(): String = when (this) {
    null -> "" // Empty when starting
    ProgramScreenDestination -> stringResource(id = R.string.nav_bar_program)
    MapScreenDestination -> stringResource(id = R.string.nav_bar_map)
    AboutUsScreenDestination -> stringResource(id = R.string.header_aboutUs)
}
