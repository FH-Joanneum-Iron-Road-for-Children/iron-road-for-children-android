package at.irfc.app.ui.spotifyPlayer

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import at.irfc.app.data.local.entity.Playlist
import at.irfc.app.data.repository.PlaylistRepository
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.compose.koinInject

@Composable
@Destination
fun SpotifyPlayerScreen(
    repository: PlaylistRepository = koinInject()
) {
    var playlist by remember { mutableStateOf<Playlist?>(null) }

    LaunchedEffect(Unit) {
        repository.getPlaylist(force = false).collect { result ->
            playlist = result.data
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
