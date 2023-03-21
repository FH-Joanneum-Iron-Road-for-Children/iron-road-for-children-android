package at.irfc.app.ui.aboutUs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.irfc.app.R
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun AboutUsScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.nav_bar_aboutUs),
            color = MaterialTheme.colorScheme.error
        )
    }
}
