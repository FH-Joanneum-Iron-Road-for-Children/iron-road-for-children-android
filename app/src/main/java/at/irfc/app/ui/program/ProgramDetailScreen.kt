package at.irfc.app.ui.program

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import at.irfc.app.R
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination

val paddingText = 16.dp
val padding = 8.dp

@Composable
@Destination
fun ProgramDetailScreen(id: Long) {
    Card(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Text(
            text = stringResource(R.string.header_programDetailScreen) + id,
            modifier = Modifier.padding(paddingText)
        )

        AsyncImage(
            model = "https://picsum.photos/200",
            contentDescription = "Some picture",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        TextBetweenPictures()

        Row() {
            AsyncImage(
                model = "https://picsum.photos/200",
                contentDescription = "Some picture",
                contentScale = ContentScale.Fit,
                modifier = Modifier.padding(padding)
            )

            AsyncImage(
                model = "https://picsum.photos/200",
                contentDescription = "Some picture",
                contentScale = ContentScale.Fit,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
fun TextBetweenPictures() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            Text(
                text = "Uhrzeit",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = paddingText,
                    end = paddingText,
                    top = 8.dp,
                    bottom = 0.dp
                )
            )

            Text(
                text = "Get time from backend",
                modifier = Modifier.padding(
                    start = paddingText,
                    end = paddingText,
                    top = 0.dp,
                    bottom = 8.dp
                )
            )

            Text(
                text = "Get Stage from backend",
                modifier = Modifier.padding(
                    start = paddingText,
                    end = paddingText,
                    top = 8.dp,
                    bottom = 0.dp
                )
            )

            Text(
                text = "Get Music Type from backend",
                modifier = Modifier.padding(
                    start = paddingText,
                    end = paddingText,
                    top = 0.dp,
                    bottom = 8.dp
                )

            )

            Text(
                text = "Get description from backend",
                modifier = Modifier.padding(paddingText)
            )
        }
    }
}
