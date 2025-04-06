package at.irfc.app.ui.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.irfc.app.generated.navigation.destinations.FullscreenImageScreenDestination
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate

@Composable
@Destination
fun GalleryDetailScreen(topicIndex: Int, navController: NavController) {
    val topicImages = listOf(
        listOf("https://example.com/topic1_img1.jpg", "https://example.com/topic1_img2.jpg"),
        listOf("https://example.com/topic2_img1.jpg", "https://example.com/topic2_img2.jpg"),
        listOf("https://example.com/topic3_img1.jpg", "https://example.com/topic3_img2.jpg"),
        listOf("https://example.com/topic4_img1.jpg", "https://example.com/topic4_img2.jpg")
    )

    val images = topicImages[topicIndex]

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(images) { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = "Detail image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(8.dp)
                    .clickable {
                        navController.navigate(
                            FullscreenImageScreenDestination(imageUrl = imageUrl)
                        )
                    }

            )
        }
    }
}

@Composable
@Destination
fun FullscreenImageScreen(imageUrl: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}
