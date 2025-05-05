package at.irfc.app.ui.spotifyPlayer

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun SpotifyPlayerScreen(
    playlistId: String
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.allowFileAccess = false
                settings.allowContentAccess = false
                webViewClient = WebViewClient()
                loadUrl("https://open.spotify.com/playlist/$playlistId")
            }
        },
        modifier = Modifier
            .fillMaxSize()
    )
}
