package ercanduman.android.audiorecorder.ui.main.recording.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ercanduman.android.audiorecorder.data.repository.RecordsRepository
import ercanduman.android.audiorecorder.ui.main.recording.delegate.UIStateHandler
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recordsRepository: RecordsRepository,
    private val uiStateHandler: UIStateHandler
) : ViewModel(), UIStateHandler by uiStateHandler {

    private var isRecordingStarted: Boolean = false
    fun onStartStopRecordingClicked() {
        if (!isRecordingStarted) {
            startRecording()
        } else {
            stopRecording()
        }
        isRecordingStarted = !isRecordingStarted
    }

    private fun startRecording() {
        recordsRepository.startRecording()
        uiStateHandler.addSnackbarMessage("Recording started.")
    }

    private fun stopRecording() {
        recordsRepository.stopRecording()
        uiStateHandler.addSnackbarMessage("Recording stopped.")
    }

    fun onShowRecordingsClicked() {
        uiStateHandler.onNavigationRequested()
    }
}
