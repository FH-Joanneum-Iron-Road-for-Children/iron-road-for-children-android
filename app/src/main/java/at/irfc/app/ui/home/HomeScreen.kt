package at.irfc.app.ui.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import at.irfc.app.R
import at.irfc.app.data.local.entity.Countdown
import at.irfc.app.data.local.entity.IntroVideo
import at.irfc.app.data.local.entity.SocialMedia
import at.irfc.app.data.repository.CountdownRepository
import at.irfc.app.data.repository.SocialMediaRepository
import at.irfc.app.data.repository.VideoRepository
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
@Destination
@RootNavGraph(start = true)
fun HomeScreen(
    countdownRepository: CountdownRepository = koinInject(),
    videoRepository: VideoRepository = koinInject(),
    socialMediaRepository: SocialMediaRepository = koinInject()
) {
    val scrollState = rememberScrollState()
    var video by remember { mutableStateOf<IntroVideo?>(null) }
    var time by remember { mutableStateOf<Countdown?>(null) }
    var socialMedia by remember { mutableStateOf<List<SocialMedia>>(emptyList()) }

    LaunchedEffect(Unit) {
        launch {
            countdownRepository.getCountdown(force = false).collect { result ->
                time = result.data?.firstOrNull()
            }
        }

        launch {
            videoRepository.getVideo(force = true).collect { result ->
                video = result.data
            }
        }
        launch {
            socialMediaRepository.getSocialMedia(force = true).collect { result ->
                socialMedia = result.data?.toList() ?: emptyList()
            }
        }
    }

    val targetDate = time?.let {
        LocalDateTime.ofEpochSecond(
            it.time / 1000,
            0,
            ZoneId.systemDefault().rules.getOffset(LocalDateTime.now())
        )
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        if (video != null) {
            VideoPlayer(video!!.path)
        }

        if (targetDate != null) {
            CountdownTimer(targetDate)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val configuration = LocalConfiguration.current
            val screenWidth = configuration.screenWidthDp.dp
            val isLandscape = configuration.orientation ==
                android.content.res.Configuration.ORIENTATION_LANDSCAPE
            val iconSize = screenWidth * if (isLandscape) 0.08f else 0.1f

            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.startbildschirm_v1),
                contentDescription = stringResource(R.string.nav_bar_map),
                contentScale = ContentScale.FillWidth
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = screenWidth * if (isLandscape) 0.5f else 0.5f),
                contentAlignment = Alignment.TopCenter
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    val facebookUrl = socialMedia.find {
                        it.title == "Facebook"
                    }?.path ?: "https://www.facebook.com/irfcfestival/"
                    val instagramUrl = socialMedia.find {
                        it.title == "Instagram"
                    }?.path ?: "https://www.instagram.com/irfc_festival/"

                    SocialIcon(
                        R.drawable.facebook,
                        "Facebook",
                        facebookUrl,
                        iconSize
                    )

                    SocialIcon(
                        R.drawable.instagram,
                        "Instagram",
                        instagramUrl,
                        iconSize
                    )
                }
            }
        }
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
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
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
            // .padding(vertical = 5.dp)
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
    )
}

@Composable
fun CountdownTimer(targetDate: LocalDateTime) {
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
            .height(180.dp)
            .padding(vertical = 5.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.countdownbackground2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val textStyle = TextStyle(
                color = Color.Yellow,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Countdown to IRFC",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                style = textStyle,
                modifier = Modifier.padding(16.dp)
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf(days, hours, minutes, seconds).forEach {
                    Text(
                        text = "$it",
                        fontSize = 38.sp,
                        textAlign = TextAlign.Center,
                        style = textStyle,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Divider(
                color = Color.White,
                thickness = 3.dp,
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Row(
                modifier = Modifier.padding(top = 4.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                listOf("DAYS", "HOURS", "MIN.", "SEC.").forEach {
                    Text(
                        text = it,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        style = textStyle,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun SocialIcon(
    iconRes: Int,
    contentDescription: String,
    url: String,
    size: Dp
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(5.dp))
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize()
        )
    }
}

fun getTotalSecondsRemaining(targetDate: LocalDateTime): Int {
    val now = LocalDateTime.now(ZoneId.systemDefault())
    val duration = Duration.between(now, targetDate)
    return duration.seconds.toInt().coerceAtLeast(0)
}
