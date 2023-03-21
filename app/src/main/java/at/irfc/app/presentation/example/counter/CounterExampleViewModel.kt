package at.irfc.app.presentation.example.counter

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CounterExampleViewModel : ViewModel() {

    private val _counter: MutableState<Int> = mutableStateOf(0)
    val counter: State<Int> = _counter

    fun increaseCount() {
        _counter.value = _counter.value + 1
    }
}
