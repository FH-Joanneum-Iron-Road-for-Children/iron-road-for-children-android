package at.irfc.app.ui.core

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.PermContactCalendar
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import at.irfc.app.R
import at.irfc.app.generated.navigation.NavGraphs
import at.irfc.app.generated.navigation.destinations.AboutUsScreenDestination
import at.irfc.app.generated.navigation.destinations.ProgramScreenDestination
import at.irfc.app.ui.theme.IrfcBlue
import at.irfc.app.ui.theme.IrfcYellow
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.utils.isRouteOnBackStackAsState

@Composable
fun BottomBar(
    navController: NavController
) {
    NavigationBar(
        containerColor = IrfcBlue
    ) {
        BottomBarDestination.values().forEach { destination ->
            NavigationBarItem(
                selected = navController.isRouteOnBackStackAsState(destination.direction).value,
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
                label = { Text(stringResource(destination.label)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = IrfcYellow,
                    selectedTextColor = IrfcYellow,
                    indicatorColor = IrfcBlue,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White
                )
            )
        }
    }
}

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Program(ProgramScreenDestination, Icons.Outlined.PermContactCalendar, R.string.nav_bar_program),

    // Map(MapScreenDestination, Icons.Default.Map, R.string.nav_bar_map),
    AboutUs(AboutUsScreenDestination, Icons.Default.MoreHoriz, R.string.nav_bar_aboutUs)
}
