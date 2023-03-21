package at.irfc.app.ui.example.counter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import at.irfc.app.presentation.example.counter.CounterExampleViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.getViewModel

@Composable
@Destination
fun CounterExampleScreen(vm: CounterExampleViewModel = getViewModel()) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CounterRow(vm)
    }
}
