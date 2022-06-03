package ercanduman.android.audiorecorder.ui.main.recording.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ercanduman.android.audiorecorder.data.repository.RecordsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recordsRepository: RecordsRepository
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    var isRecordingStarted: Boolean = false
    fun onStartStopRecordingClicked() {
        if (!isRecordingStarted) {
            startRecording()
        } else {
            stopRecording()
        }
        isRecordingStarted = !isRecordingStarted
    }

    private fun startRecording() {
        // recordsRepository.startRecording()
        _homeUiState.update { currentState ->
            currentState.copy(snackbarMessages = currentState.snackbarMessages.addNewMessage("Recording started."))
        }
    }

    private fun stopRecording() {
        // recordsRepository.stopRecording()
        _homeUiState.update { currentState ->
            currentState.copy(snackbarMessages = currentState.snackbarMessages.addNewMessage("Recording stopped."))
        }
    }

    fun onShowRecordingsClicked() {
        _homeUiState.update { currentState -> currentState.copy(navigateToRecordings = true) }
    }

    fun onShowRecordingsProcessed() {
        _homeUiState.update { currentState -> currentState.copy(navigateToRecordings = false) }
    }

    fun onSnackbarMessageShown(messageId: Long) {
        _homeUiState.update { currentState ->
            val messages = currentState.snackbarMessages.filterNot { it.id == messageId }
            currentState.copy(snackbarMessages = messages)
        }
    }

    /**
     * In order to learn more information and handle UI events by the ViewModel,
     * the following url can be checked.
     * https://developer.android.com/topic/architecture/ui-layer/events
     */
    data class HomeUiState(
        val navigateToRecordings: Boolean = false,
        val snackbarMessages: List<SnackbarMessage> = emptyList()
    )

    data class SnackbarMessage(
        val id: Long = UUID.randomUUID().mostSignificantBits,
        val message: String = ""
    )

    private fun List<SnackbarMessage>.addNewMessage(message: String): List<SnackbarMessage> {
        return this + SnackbarMessage(message = message)
    }
}
