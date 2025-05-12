package at.irfc.app.ui.aboutUs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChildCare
import androidx.compose.material.icons.outlined.DirectionsCarFilled
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.QueueMusic
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.irfc.app.R
import at.irfc.app.data.local.entity.Playlist
import at.irfc.app.data.repository.PlaylistRepository
import at.irfc.app.generated.navigation.NavGraphs
import at.irfc.app.generated.navigation.destinations.GalleryScreenDestination
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
    var playlist by remember { mutableStateOf<Playlist?>(null) }
    LaunchedEffect(Unit) {
        repository.getPlaylist(force = false).collect { result ->
            playlist = result.data
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val uriHandler = LocalUriHandler.current
        // Spacer(modifier = Modifier.height(20.dp))
        ExpandableCard(
            unexpandedLines = 3,
            text = stringResource(R.string.aboutUs_text)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(3f)
                    .aspectRatio(3f / 2f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.shs),
                    contentDescription = stringResource(R.string.shs),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .weight(2f)
                    .aspectRatio(3f / 2f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.lid),
                    contentDescription = stringResource(R.string.lid),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
        Column(
            modifier = Modifier
                // .padding(bottom = 15.dp)
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
                    uriHandler.openUri("https://irfc.at/home/spendenkinderprojekte/")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChildCare,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = stringResource(R.string.aboutUs_children),
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            OutlinedButton(
                onClick = {
                    uriHandler.openUri("https://irfc.at/shop/")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = stringResource(R.string.aboutUs_shop),
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            OutlinedButton(
                onClick = {
                    uriHandler.openUri("https://irfc.at/am-event/#verlosung")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Outlined.DirectionsCarFilled,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = stringResource(R.string.aboutUs_verlosung),
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            OutlinedButton(
                onClick = {
                    uriHandler.openUri("https://open.spotify.com/playlist/" + playlist?.spotifyId)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    // .padding(vertical = 6.dp)
                    .clickable { uriHandler.openUri("https://irfc.at/kontakt/impressum") },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.aboutUs_imprint) + "  >",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    // .padding(vertical = 6.dp) // controls height
                    .clickable { uriHandler.openUri("https://irfc.at/kontakt/datenschutz") },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Shield,
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.aboutUs_privacy) + "  >",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                )
            }
        }
        Text(
            text = stringResource(R.string.aboutUs_providedBy),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
        // Spacer(modifier = Modifier.height(20.dp))
    }
}
