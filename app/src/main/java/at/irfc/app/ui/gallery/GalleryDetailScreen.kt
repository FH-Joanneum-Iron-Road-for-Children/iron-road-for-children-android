package at.irfc.app.ui.gallery

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.irfc.app.data.local.entity.Gallery
import at.irfc.app.data.repository.GalleryRepository
import at.irfc.app.ui.core.ZoomableImage
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.compose.koinInject

@Destination
@Composable
fun GalleryDetailScreen(
    galleryId: Long,
    navController: NavController,
    repository: GalleryRepository = koinInject()
) {
    var gallery by remember { mutableStateOf<Gallery?>(null) }

    LaunchedEffect(galleryId) {
        repository.loadGallery(force = false).collect { result ->
            gallery = result.data?.find { it.id == galleryId }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (gallery != null) {
            val painter = rememberAsyncImagePainter(gallery!!.path)

            ZoomableImage(
                minScale = 1f,
                maxScale = 5f,
                painter = painter,
                contentDescription = gallery!!.title
            )

            Text(
                text = gallery!!.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(8.dp)
            )
        }

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close"
            )
        }
    }
}
