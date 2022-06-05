package ercanduman.android.audiorecorder.ui.main.recording.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ercanduman.android.audiorecorder.data.repository.RecordsRepository
import ercanduman.android.audiorecorder.ui.main.recording.delegate.UIStateHandler
import ercanduman.android.audiorecorder.ui.main.recording.delegate.UIStateHandlerDelegate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recordsRepository: RecordsRepository,
    private val uiStateHandlerDelegate: UIStateHandlerDelegate
) : ViewModel(), UIStateHandler by uiStateHandlerDelegate {

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
        uiStateHandlerDelegate.addSnackbarMessage("Recording started.")
    }

    private fun stopRecording() {
        recordsRepository.stopRecording()
        uiStateHandlerDelegate.addSnackbarMessage("Recording stopped.")
    }

    fun onShowRecordingsClicked() {
        uiStateHandlerDelegate.onNavigationRequested()
    }

    fun onShowRecordingsProcessed() {
        uiStateHandlerDelegate.onNavigationProcessed()
    }
}
