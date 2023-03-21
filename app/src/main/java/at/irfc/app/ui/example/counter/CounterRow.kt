package at.irfc.app.ui.example.counter

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import at.irfc.app.presentation.example.counter.CounterExampleViewModel

@Composable
fun CounterRow(vm: CounterExampleViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = vm.counter.value.toString())
        Button(onClick = { vm.increaseCount() }) {
            Text(text = "Increase")
        }
    }
}
