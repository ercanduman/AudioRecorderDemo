package ercanduman.android.audiorecorder.ui.main.recording.delegate

import kotlinx.coroutines.flow.StateFlow

interface UIStateHandler {
    val uiState: StateFlow<UiState>

    fun addSnackbarMessage(message: String)
    fun addSnackbarMessage(message: String, undoCallback: SnackbarUndoCallback)
    fun onSnackbarMessageProcessed(messageId: Long)

    fun onNavigationRequested()
    fun onNavigationProcessed()
}