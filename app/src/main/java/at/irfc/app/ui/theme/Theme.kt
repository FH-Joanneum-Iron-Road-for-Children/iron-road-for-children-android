package at.irfc.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme
    @Composable
    get() = darkColorScheme(
        primary = IrfcYellow,
        onPrimary = IrfcBlue,
        secondary = IrfcBlue,
        onSecondary = IrfcYellow
    )

private val LightColorScheme
    @Composable
    get() = lightColorScheme(
        primary = IrfcYellow,
        onPrimary = IrfcBlue,
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
