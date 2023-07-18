package at.irfc.app.ui.core

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconText(iconVector: ImageVector, iconDescription: String?, text: String) {
    Row {
        Icon(iconVector, iconDescription)
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = text
        )
    }
}
