package ercanduman.android.audiorecorder.ui.main.recording.list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ercanduman.android.audiorecorder.data.model.Record
import ercanduman.android.audiorecorder.data.repository.RecordsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RecordingsViewModel @Inject constructor(
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
