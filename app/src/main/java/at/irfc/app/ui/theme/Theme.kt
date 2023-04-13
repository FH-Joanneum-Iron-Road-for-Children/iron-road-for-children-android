package at.irfc.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = IrfcYellow,
    onPrimary = Color.Black,
    secondary = IrfcBlue,
    onSecondary = IrfcYellow
)

private val LightColorScheme = lightColorScheme(
    primary = IrfcYellow,
    onPrimary = Color.Black,
    secondary = IrfcBlue,
    onSecondary = IrfcYellow
)

@Composable
fun IronRoadForChildrenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
