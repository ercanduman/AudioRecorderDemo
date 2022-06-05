package ercanduman.android.audiorecorder.ui.main.recording.list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ercanduman.android.audiorecorder.data.model.Record
import ercanduman.android.audiorecorder.data.repository.RecordsRepository
import ercanduman.android.audiorecorder.ui.main.recording.delegate.SnackbarUndoCallback
import ercanduman.android.audiorecorder.ui.main.recording.delegate.UIStateHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RecordingsViewModel @Inject constructor(
    private val recordsRepository: RecordsRepository,
    private val uiStateHandler: UIStateHandler
) : ViewModel(), UIStateHandler by uiStateHandler {

    val records: Flow<List<Record>> = recordsRepository.records

    private var isPlayingStarted: Boolean = false
    fun onPlayPauseRecordClicked(filePath: String) {
        if (!isPlayingStarted) {
            onPlayClicked(filePath)
        } else {
            onPauseClicked()
        }
        isPlayingStarted = !isPlayingStarted
    }

    private fun onPlayClicked(filePath: String) {
        recordsRepository.startPlaying(filePath)
        uiStateHandler.addSnackbarMessage("Record is playing...")
    }

    private fun onPauseClicked() {
        recordsRepository.stopPlaying()
        uiStateHandler.addSnackbarMessage("Record is stopped.")
    }

    private var deletedRecord: Record? = null
    fun onSwipeToDelete(record: Record) {
        recordsRepository.deleteRecord(record)
        deletedRecord = record
        uiStateHandler.addSnackbarMessage(
            message = "Record is deleted.",
            undoCallback = swipeDeleteUndo
        )
    }

    private val swipeDeleteUndo = object : SnackbarUndoCallback {
        override fun undoClicked() {
            onSwipeDeleteUndo()
        }
    }

    private fun onSwipeDeleteUndo() {
        deletedRecord?.let { recordsRepository.insertRecord(it) }
        uiStateHandler.addSnackbarMessage("Undo clicked.")
    }
}
