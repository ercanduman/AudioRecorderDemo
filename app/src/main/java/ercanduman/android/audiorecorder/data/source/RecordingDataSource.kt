package ercanduman.android.audiorecorder.data.source

import ercanduman.android.audiorecorder.data.model.Record
import kotlinx.coroutines.flow.StateFlow

interface RecordingDataSource {
    val records: StateFlow<List<Record>>

    fun startRecording()
    fun stopRecording()

    fun insertRecord(record: Record)
    fun deleteRecord(record: Record)
}
