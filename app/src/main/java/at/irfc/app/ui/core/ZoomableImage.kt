package at.irfc.app.ui.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import at.irfc.app.R

@Composable
fun zoomableImage(minScale: Float, maxScale: Float) {
    var zoom by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var size by remember { mutableStateOf(IntSize.Zero) }

    Image(
        painter = painterResource(id = R.drawable.map),
        contentDescription = stringResource(R.string.nav_bar_map),
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds()
            .pointerInput(Unit) {
                detectTransformGestures(
                    onGesture = { _, gesturePan, gestureZoom, _ ->

                        val newScale = (zoom * gestureZoom).coerceIn(minScale, maxScale)
                        val newOffset = offset + gesturePan
                        zoom = newScale

                        val maxX = (size.width * (zoom - 1) / 2f)
                        val maxY = (size.height * (zoom - 1) / 2f)

                        offset = Offset(
                            newOffset.x.coerceIn(-maxX, maxX),
                            newOffset.y.coerceIn(-maxY, maxY)
                        )
                    }
                )
            }
            .onSizeChanged {
                size = it
            }
            .graphicsLayer {
                translationX = offset.x
                translationY = offset.y
                scaleX = zoom
                scaleY = zoom
            }
    )
}
