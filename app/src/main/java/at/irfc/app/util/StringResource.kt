package at.irfc.app.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

class StringResource(@StringRes private val resId: Int, vararg arguments: Any) {
    private val arguments: Array<out Any>

    init {
        this.arguments = arguments
    }

    fun getMessage(context: Context): String {
        return context.resources.getString(resId, *arguments)
    }

    @Composable
    fun getMessage(): String = stringResource(resId, *arguments)
}
