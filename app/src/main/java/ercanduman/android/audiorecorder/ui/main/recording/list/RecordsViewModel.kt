package ercanduman.android.audiorecorder.ui.main.recording.list

import androidx.lifecycle.ViewModel
import ercanduman.android.audiorecorder.data.model.Record
import ercanduman.android.audiorecorder.data.repository.RecordsRepository
import kotlinx.coroutines.flow.Flow

class RecordsViewModel(
    private val recordsRepository: RecordsRepository
) : ViewModel() {

    val records: Flow<List<Record>> = recordsRepository.records

    fun onPlayClicked(record: Record) {
        recordsRepository.startPlaying(record)
    }

    fun onPauseClicked() {
        recordsRepository.stopPlaying()
    }

    fun onSwipeDeleted(record: Record) {
        recordsRepository.deleteRecord(record)
    }

    fun onSwipeDeleteUndo(record: Record) {
        recordsRepository.deleteRecord(record)
    }
}
