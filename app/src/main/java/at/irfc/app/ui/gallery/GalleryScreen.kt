package at.irfc.app.ui.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.irfc.app.generated.navigation.destinations.GalleryDetailScreenDestination
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate

@Composable
@Destination
fun GalleryScreen(navController: NavController) {
    val topics = listOf(
        GalleryItem(
            title = "Alle",
            imageUrl = "https://cdn3.fh-joanneum.at/media/" +
                "2023/08/fh-joanneum-fuer-iron-road-for-children-1024x512.jpg"
        ),
        GalleryItem(
            title = "Main Stage",
            imageUrl = "https://example.com/image2.jpg"
        ),
        GalleryItem(
            title = "Bikes",
            imageUrl = "https://example.com/image3.jpg"
        ),
        GalleryItem(
            title = "US-Cars",
            imageUrl = "https://example.com/image4.jpg"
        ),
        GalleryItem(
            title = "Vespas",
            imageUrl = "https://example.com/image5.jpg"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        topics.forEachIndexed { index, topic ->
            Column(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .clickable {
                        navController.navigate(GalleryDetailScreenDestination(topicIndex = index))
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = topic.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier.size(200.dp)
                ) {
                    AsyncImage(
                        model = topic.imageUrl,
                        contentDescription = topic.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

data class GalleryItem(
    val title: String,
    val imageUrl: String
)
