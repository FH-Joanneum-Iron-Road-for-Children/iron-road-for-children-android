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
import at.irfc.app.data.repository.GalleryPictureRepository
import at.irfc.app.ui.core.ZoomableImage
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.compose.koinInject

@Destination
@Composable
fun GalleryDetailScreen(
    pictureId: Long,
    navController: NavController,
    repository: GalleryPictureRepository = koinInject()
) {
    var picture by remember { mutableStateOf<at.irfc.app.data.local.entity.GalleryPicture?>(null) }

    LaunchedEffect(pictureId) {
        repository.loadPictures(force = false).collect { result ->
            picture = result.data?.find { it.id == pictureId }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (picture != null) {
            val painter = rememberAsyncImagePainter(picture!!.path)

            ZoomableImage(
                minScale = 1f,
                maxScale = 5f,
                painter = painter,
                contentDescription = picture!!.title
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
