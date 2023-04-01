package at.irfc.app.ui.map

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import at.irfc.app.R
import at.irfc.app.ui.core.*
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun MapScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ZoomableImage(
            painter = painterResource(id = R.drawable.map),
            contentDescription = stringResource(R.string.nav_bar_map),
            minScale = 1f,
            maxScale = 3f
        )
    }
}
