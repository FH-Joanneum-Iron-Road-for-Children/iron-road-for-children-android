package at.irfc.app.ui.gallery

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import at.irfc.app.data.local.entity.Gallery
import at.irfc.app.data.repository.GalleryRepository
import at.irfc.app.generated.navigation.destinations.GalleryDetailScreenDestination
import at.irfc.app.util.Resource
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.compose.koinInject

@Composable
@Destination
fun GalleryScreen(
    navigator: DestinationsNavigator,
    repository: GalleryRepository = koinInject(),
) {
    var gallery by remember { mutableStateOf<List<Gallery>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val failedImages = remember { mutableStateListOf<Long>() }

    LaunchedEffect(Unit) {
        repository.loadGallery(force = false).collect { result ->
            when (result) {
                is Resource.Success -> {
                    // TEMP: only keep URLs that look valid
                    gallery =
                        result.data.filter {
                            it.path.startsWith("http") &&
                                it.path.endsWith(".jpg") ||
                                it.path.endsWith(
                                    ".png",
                                )
                        }
                    isLoading = false
                }
                is Resource.Error -> {
                    isLoading = false
                }
                is Resource.Loading -> {
                    isLoading = true
                }
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text("Loading...")
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 120.dp),
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val filteredPictures = gallery.filter { it.id !in failedImages }

            itemsIndexed(filteredPictures) { _, gallery ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                    modifier =
                        Modifier
                            .aspectRatio(1f)
                            .clickable {
                                navigator.navigate(
                                    GalleryDetailScreenDestination(galleryId = gallery.id),
                                )
                            },
                ) {
                    AsyncImage(
                        model = gallery.path,
                        contentDescription = gallery.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        onError = {
                            Log.e("GalleryImage", "Image failed: ${gallery.path}")
                            failedImages.add(gallery.id)
                        },
                    )
                }
            }
        }
    }
}
