package at.irfc.app.ui.spotifyPlayer

import android.content.Intent
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
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

                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            val url = request?.url.toString()
                            return when {
                                url.startsWith("http") -> false
                                url.startsWith("intent:") || url.startsWith("spotify:") -> {
                                    try {
                                        val intent = Intent.parseUri(
                                            url,
                                            Intent.URI_INTENT_SCHEME
                                        )
                                        if (
                                            intent.resolveActivity(
                                                context.packageManager
                                            ) != null
                                        ) {
                                            context.startActivity(intent)
                                        } else {
                                            val fallbackUrl =
                                                intent.getStringExtra("browser_fallback_url")
                                            if (fallbackUrl != null) {
                                                view?.loadUrl(fallbackUrl)
                                            }
                                        }
                                    } catch (e: Exception) {
                                        Log.e(
                                            "SpotifyWebView",
                                            "Fehler beim Öffnen von URL: $url",
                                            e
                                        )
                                    }
                                    true
                                }

                                else -> true
                            }
                        }
                    }

                    loadUrl(
                        "https://open.spotify.com/playlist/${playlist!!.spotifyId}"
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
