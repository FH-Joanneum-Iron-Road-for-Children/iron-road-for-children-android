package at.irfc.app.ui.aboutUs

import androidx.compose.foundation.layout.*
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
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.aboutUs_text)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween,
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

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Outlined.Info, contentDescription = null)
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = stringResource(R.string.aboutUs_imprint)
                        )
                    }
                    OutlinedButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Outlined.Shield, contentDescription = null)
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = stringResource(R.string.aboutUs_privacy)
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.aboutUs_providedBy),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview
@Composable
private fun AboutUsScreenPreview() {
    IronRoadForChildrenTheme {
        AboutUsScreen()
    }
}
