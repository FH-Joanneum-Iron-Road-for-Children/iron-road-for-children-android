package at.irfc.app.ui.map

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import at.irfc.app.ui.core.*
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun MapScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        zoomableImage(minScale = 1f, maxScale = 3f)
    }
}
