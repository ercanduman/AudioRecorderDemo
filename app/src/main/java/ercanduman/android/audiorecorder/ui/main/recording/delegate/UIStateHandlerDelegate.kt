package ercanduman.android.audiorecorder.ui.main.recording.delegate

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UIStateHandlerDelegate : UIStateHandler {

    private val _uiState = MutableStateFlow(UiState())
    override val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    override fun addSnackbarMessage(message: String) {
        _uiState.update { currentState ->
            currentState.copy(snackbarMessages = currentState.snackbarMessages.addNewMessage(message = message))
        }
    }

    override fun addSnackbarMessage(message: String, undoCallback: SnackbarUndoCallback) {
        _uiState.update { currentState ->
            currentState.copy(
                snackbarMessages = currentState.snackbarMessages.addNewMessage(
                    message = message,
                    undoCallback = undoCallback
                )
            )
        }
    }

    override fun onSnackbarMessageProcessed(messageId: Long) {
        _uiState.update { currentState ->
            val messages = currentState.snackbarMessages.filterNot { it.id == messageId }
            currentState.copy(snackbarMessages = messages)
        }
    }

    override fun onNavigationRequested() {
        _uiState.update { currentState -> currentState.copy(onNavigateClicked = true) }
    }

    override fun onNavigationProcessed() {
        _uiState.update { currentState -> currentState.copy(onNavigateClicked = false) }
    }

    private fun List<SnackbarMessage>.addNewMessage(message: String): List<SnackbarMessage> {
        return this + SnackbarMessage(message = message)
    }

    private fun List<SnackbarMessage>.addNewMessage(
        message: String,
        undoCallback: SnackbarUndoCallback
    ): List<SnackbarMessage> {
        return this + SnackbarMessage(message = message, undoCallback = undoCallback)
    }
}
