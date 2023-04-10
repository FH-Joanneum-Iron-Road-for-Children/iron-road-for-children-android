package at.irfc.app.ui.core

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import at.irfc.app.R
import at.irfc.app.generated.navigation.NavGraphs
import at.irfc.app.generated.navigation.appCurrentDestinationAsState
import at.irfc.app.generated.navigation.destinations.AboutUsScreenDestination
import at.irfc.app.generated.navigation.destinations.Destination
import at.irfc.app.generated.navigation.destinations.MapScreenDestination
import at.irfc.app.generated.navigation.destinations.ProgramScreenDestination
import at.irfc.app.generated.navigation.startAppDestination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

@Composable
fun BottomBar(
    navController: NavController
) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    NavigationBar {
        BottomBarDestination.values().forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigate(destination.direction) {
                        popUpTo(NavGraphs.root)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(destination.label)
                    )
                },
                label = { Text(stringResource(destination.label)) }
            )
        }
    }
}

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Program(ProgramScreenDestination, Icons.Default.List, R.string.nav_bar_program),
    Map(MapScreenDestination, Icons.Default.Map, R.string.nav_bar_map),
    AboutUs(AboutUsScreenDestination, Icons.Default.MoreHoriz, R.string.nav_bar_aboutUs)
}
