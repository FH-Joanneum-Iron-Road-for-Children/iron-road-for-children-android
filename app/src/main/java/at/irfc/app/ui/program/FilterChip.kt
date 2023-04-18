package at.irfc.app.ui.program

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.irfc.app.R
import at.irfc.app.ui.theme.IrfcYellow
import at.irfc.app.ui.theme.IronRoadForChildrenTheme

@Composable
fun FilterChip(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        if (selected) IrfcYellow else MaterialTheme.colorScheme.background
    )
    val contentPadding = if (selected) {
        ButtonDefaults.ButtonWithIconContentPadding
    } else {
        ButtonDefaults.ContentPadding
    }

    Button(
        modifier = modifier,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = Color.Black
        ),
        contentPadding = contentPadding,
        onClick = onClick
    ) {
        if (selected) {
            Icon(
                Icons.Filled.Close,
                contentDescription = stringResource(id = R.string.programmScreen_clearFilter)
            )
        }
        Text(text)
    }
}

@Composable
@Preview
fun FilterChipPreview() {
    IronRoadForChildrenTheme {
        Row {
            FilterChip(text = "Selected", selected = true, onClick = {})
            FilterChip(text = "NotSelected", selected = false, onClick = {})
        }
    }
}
