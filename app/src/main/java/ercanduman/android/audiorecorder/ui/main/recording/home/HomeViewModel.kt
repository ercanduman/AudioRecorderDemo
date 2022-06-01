package ercanduman.android.audiorecorder.ui.main.recording.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ercanduman.android.audiorecorder.data.repository.RecordsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val recordsRepository: RecordsRepository
) : ViewModel() {

    private val _snackbarMessage = MutableStateFlow("")
    val snackbarMessage: StateFlow<String> = _snackbarMessage

    private val _navigateToRecordsClicked = MutableStateFlow(false)
    val navigateToRecordsClicked: StateFlow<Boolean> = _navigateToRecordsClicked

    fun onRecordStarted() = viewModelScope.launch {
        recordsRepository.startRecording()
        _snackbarMessage.value = "Record started."
    }

    fun onRecordStopped() = viewModelScope.launch {
        recordsRepository.stopRecording()
        _snackbarMessage.value = "Record stopped."
    }

    fun onShowRecordsClicked() {
        _navigateToRecordsClicked.value = true
    }
}
