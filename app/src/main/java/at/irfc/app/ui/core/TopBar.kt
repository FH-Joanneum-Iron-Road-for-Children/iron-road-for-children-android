package at.irfc.app.ui.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import at.irfc.app.R
import at.irfc.app.generated.navigation.destinations.ProgramScreenDestination
import at.irfc.app.generated.navigation.destinations.TypedDestination
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.utils.currentDestinationAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    val destination = navController.currentDestinationAsState().value
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
private fun DestinationSpec<*>?.screenTitle(): String = when (this as? TypedDestination) {
    null -> "" // Empty when starting
    ProgramScreenDestination -> stringResource(id = R.string.nav_bar_program)
}
