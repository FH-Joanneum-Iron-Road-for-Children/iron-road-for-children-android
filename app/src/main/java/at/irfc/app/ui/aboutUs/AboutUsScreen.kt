package at.irfc.app.ui.aboutUs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.irfc.app.R
import at.irfc.app.ui.core.icons.Donate
import at.irfc.app.ui.core.icons.IrfcIcons
import at.irfc.app.ui.theme.IronRoadForChildrenTheme
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun AboutUsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.weight(1f, fill = false)
        ) {
            Box(Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    modifier = Modifier.padding(15.dp),
                    text = stringResource(R.string.aboutUs_text),
                    textAlign = TextAlign.Justify
                )
            }
        }

        Column(
            modifier = Modifier.weight(0.9f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val uriHandler = LocalUriHandler.current

            Button(onClick = { uriHandler.openUri("https://irfc.at/#spenden") }) {
                Icon(IrfcIcons.Donate, contentDescription = null)
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = stringResource(R.string.aboutUs_donate)
                )
            }

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedButton(
                    onClick = { uriHandler.openUri("https://irfc.at/kontakt/impressum") }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = stringResource(R.string.aboutUs_imprint),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                OutlinedButton(
                    onClick = { uriHandler.openUri("https://irfc.at/kontakt/datenschutz") }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Shield,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = stringResource(R.string.aboutUs_privacy),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }

        Text(
            text = stringResource(R.string.aboutUs_providedBy),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
private fun AboutUsScreenPreview() {
    IronRoadForChildrenTheme {
        Surface {
            AboutUsScreen()
        }
    }
}
