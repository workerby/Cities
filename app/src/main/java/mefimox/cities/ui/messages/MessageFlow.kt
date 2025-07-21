package mefimox.cities.ui.messages

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object MessageFlow {
    private val messageFlow = MutableSharedFlow<Int>()

    operator fun invoke() = messageFlow.asSharedFlow()

    suspend fun emit(@StringRes id: Int) {
        messageFlow.emit(id)
    }
}
