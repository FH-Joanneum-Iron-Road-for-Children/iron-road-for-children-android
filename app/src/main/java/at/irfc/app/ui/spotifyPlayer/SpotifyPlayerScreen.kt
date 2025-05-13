package at.irfc.app.ui.spotifyPlayer

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import at.irfc.app.data.local.entity.Playlist
import at.irfc.app.data.repository.PlaylistRepository
import at.irfc.app.generated.navigation.NavGraphs
import at.irfc.app.ui.core.TopBar
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.compose.koinInject

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Destination
fun SpotifyPlayerScreen(
    repository: PlaylistRepository = koinInject()
) {
    var playlist by remember { mutableStateOf<Playlist?>(null) }
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        repository.getPlaylist(force = false).collect { result ->
            playlist = result.data
        }
    }

    Scaffold(
        topBar = {
            TopBar(navController)
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
        ) {
            DestinationsNavHost(
                navController = navController as NavHostController,
                navGraph = NavGraphs.root
            )
        }
    }

    if (playlist != null) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.allowFileAccess = false
                    settings.allowContentAccess = false
                    webViewClient = WebViewClient()
                    loadUrl("https://open.spotify.com/playlist/${playlist!!.spotifyId}")
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    } else {
        // Show a loading indicator while the playlist is being fetched
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
