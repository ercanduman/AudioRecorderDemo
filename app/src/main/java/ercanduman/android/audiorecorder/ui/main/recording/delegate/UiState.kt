package ercanduman.android.audiorecorder.ui.main.recording.delegate

/**
 * In order to learn more information and handle UI events by the ViewModel,
 * the following url can be checked.
 * https://developer.android.com/topic/architecture/ui-layer/events
 */
data class UiState(
    val onNavigateClicked: Boolean = false,
    val snackbarMessages: List<SnackbarMessage> = emptyList()
)


