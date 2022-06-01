package ercanduman.android.audiorecorder.data.source

import android.media.MediaRecorder
import android.util.Log
import ercanduman.android.audiorecorder.data.filename.NameAndPathProvider
import ercanduman.android.audiorecorder.data.model.Record
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TAG = "RecordingDataSourceImpl"

class RecordingDataSourceImpl(
    private val recorder: MediaRecorder,
    private val nameAndPathProvider: NameAndPathProvider
) : RecordingDataSource {
    private val currentRecord = Record(nameAndPathProvider.provideName(), nameAndPathProvider.provideFilePath())
    private val currentRecordList = mutableListOf<Record>()

    override val records: Flow<List<Record>> = flow { emit(currentRecordList) }
    override fun startRecording() {
        recorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(currentRecord.path)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: Throwable) {
                Log.e(TAG, "prepare() failed. $e")
            }

            start()
        }
    }

    override fun stopRecording() {
        recorder.apply {
            stop()
            release()
        }

        insertRecord(currentRecord.copy(stoppedTime = System.currentTimeMillis()))
    }

    override fun insertRecord(record: Record) {
        currentRecordList.add(record)
    }

    override fun deleteRecord(record: Record) {
        currentRecordList.remove(record)
    }
}
