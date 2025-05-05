package at.irfc.app.ui.aboutUs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.QueueMusic
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.irfc.app.R
import at.irfc.app.data.repository.PlaylistRepository
import at.irfc.app.generated.navigation.NavGraphs
import at.irfc.app.generated.navigation.destinations.GalleryScreenDestination
import at.irfc.app.generated.navigation.destinations.SpotifyPlayerScreenDestination
import at.irfc.app.ui.core.ExpandableCard
import at.irfc.app.ui.core.icons.Donate
import at.irfc.app.ui.core.icons.IrfcIcons
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import org.koin.compose.koinInject

@Composable
@Destination
fun AboutUsScreen(repository: PlaylistRepository = koinInject(), navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val uriHandler = LocalUriHandler.current

        Spacer(modifier = Modifier.height(20.dp))
        ExpandableCard(
            modifier = Modifier.padding(bottom = 15.dp),
            unexpandedLines = 5,
            text = stringResource(R.string.aboutUs_text)
        )

        Column(
            modifier = Modifier
                .padding(bottom = 15.dp)
                .width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { uriHandler.openUri("https://irfc.at/#spenden") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(IrfcIcons.Donate, contentDescription = null)
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = stringResource(R.string.aboutUs_donate)
                )
            }

            OutlinedButton(
                onClick = {
                    uriHandler.openUri("https://irfc.at/app/app-gewinnspiel/")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.aboutUs_raffle),
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            OutlinedButton(
                onClick = {
                    // uriHandler.openUri("https://open.spotify.com/playlist/" + playlist?.spotifyId)
                    navController.navigate(
                        // SpotifyPlayerScreenDestination(playlist?.spotifyId ?: "")
                        SpotifyPlayerScreenDestination
                    ) {
                        popUpTo(NavGraphs.root)
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Outlined.QueueMusic,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = stringResource(R.string.aboutUs_playlist),
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            OutlinedButton(
                onClick = {
                    navController.navigate(GalleryScreenDestination) {
                        popUpTo(NavGraphs.root)
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Outlined.Image,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = stringResource(R.string.aboutUs_gallery),
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }

        Column(
            modifier = Modifier.padding(bottom = 15.dp)
                .width(IntrinsicSize.Max),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = { uriHandler.openUri("https://irfc.at/kontakt/impressum") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = stringResource(R.string.aboutUs_imprint),
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            OutlinedButton(
                onClick = { uriHandler.openUri("https://irfc.at/kontakt/datenschutz") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Outlined.Shield,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = stringResource(R.string.aboutUs_privacy),
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }

        Text(
            text = stringResource(R.string.aboutUs_providedBy),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}
