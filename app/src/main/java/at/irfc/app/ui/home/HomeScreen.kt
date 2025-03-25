package at.irfc.app.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun HomeScreen() {
    Text(
        text = "Home Screen",
        textAlign = TextAlign.Center
    )
}
