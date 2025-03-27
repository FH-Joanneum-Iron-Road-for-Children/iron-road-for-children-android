package at.irfc.app.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import at.irfc.app.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import kotlinx.coroutines.delay

@Composable
@Destination
@RootNavGraph(start = true)
fun HomeScreen() {
    val videoUrl =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    // val imageUrl = "https://example.com/image.jpg"

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VideoPlayer(videoUrl)

        CountdownTimer()

        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.startbildschirm_v1),
            contentDescription = stringResource(R.string.nav_bar_map),
            contentScale = ContentScale.Crop // Schneidet das Bild passend zur Breite
        )
    }
}

@Composable
fun VideoPlayer(videoUrl: String) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.Builder()
                .setUri(videoUrl)
                .setMimeType(MimeTypes.APPLICATION_MP4)
                .build()
            setMediaItem(mediaItem)
            prepare()
            volume = 0f
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
            }
        },
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
    )
}

@Composable
fun CountdownTimer() {
    val targetDate = LocalDateTime.of(2025, 6, 19, 0, 0)
    var timeLeft by remember { mutableStateOf(getTotalSecondsRemaining(targetDate)) }

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft = getTotalSecondsRemaining(targetDate)
        }
    }

    val days = timeLeft / (24 * 3600)
    val hours = (timeLeft % (24 * 3600)) / 3600
    val minutes = (timeLeft % 3600) / 60
    val seconds = timeLeft % 60

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(vertical = 5.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.countdownbackground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Countdown to IRFC 2025",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = androidx.compose.ui.graphics.Color.Yellow,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "$days",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.Yellow,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$hours",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.Yellow,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$minutes",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.Yellow,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$seconds",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.Yellow,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.padding(top = 4.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "DAYS",
                    color = androidx.compose.ui.graphics.Color.Yellow,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "HOURS",
                    color = androidx.compose.ui.graphics.Color.Yellow,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "MIN.",
                    color = androidx.compose.ui.graphics.Color.Yellow,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "SEC.",
                    color = androidx.compose.ui.graphics.Color.Yellow,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

fun getTotalSecondsRemaining(targetDate: LocalDateTime): Int {
    val now = LocalDateTime.now(ZoneId.systemDefault())
    val duration = Duration.between(now, targetDate)
    return duration.seconds.toInt().coerceAtLeast(0)
}
