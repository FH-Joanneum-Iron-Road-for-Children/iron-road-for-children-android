package at.irfc.app.ui.voting

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import at.irfc.app.R

@Composable
fun ConfirmVotingDialog(
    votingEventName: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(stringResource(id = R.string.voting_confirmVoting))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancel
            ) {
                Text(stringResource(id = R.string.cancel))
            }
        },
        title = {
            Text(stringResource(id = R.string.voting_confirmVotingTitle))
        },
        text = {
            Text(stringResource(id = R.string.voting_confirmVotingText, votingEventName))
        }
    )
}
