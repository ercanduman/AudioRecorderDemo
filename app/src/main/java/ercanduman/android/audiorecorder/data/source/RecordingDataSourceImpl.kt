package ercanduman.android.audiorecorder.data.source

import android.media.MediaRecorder
import ercanduman.android.audiorecorder.data.filename.NameAndPathProvider
import ercanduman.android.audiorecorder.data.model.Record
import ercanduman.android.audiorecorder.internal.util.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.atomic.AtomicInteger

class RecordingDataSourceImpl(
    private val recorder: MediaRecorder,
    private val nameAndPathProvider: NameAndPathProvider
) : RecordingDataSource {
    private val atomicInteger = AtomicInteger(0)

    private var currentRecord: Record? = null

    private val currentRecordList = mutableListOf<Record>()

    private val _records: MutableStateFlow<List<Record>> = MutableStateFlow(emptyList())
    override val records: StateFlow<List<Record>> = _records.asStateFlow()

    override fun startRecording() {
        recorder.apply {
            val audioId = atomicInteger.incrementAndGet()
            currentRecord = Record(
                name = nameAndPathProvider.provideName(audioId),
                path = nameAndPathProvider.provideFilePath(audioId)
            )

            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(currentRecord?.path)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: Throwable) {
                Logger.log("prepare() failed. $e")
            }

            start()
        }
    }

    /**
     * For more information about how to use MediaRecorder:
     * https://developer.android.com/reference/android/media/MediaRecorder
     */
    override fun stopRecording() {
        recorder.apply {
            stop()
            reset() // You can reuse the object by going back to setAudioSource() step
            // release() // Now the object cannot be reused
        }

        currentRecord?.let { insertRecord(it.copy(stoppedTime = System.currentTimeMillis())) }
    }

    override fun insertRecord(record: Record) {
        currentRecordList.add(record)
        _records.update { currentRecordList }
    }

    override fun deleteRecord(record: Record) {
        currentRecordList.remove(record)
        _records.update { currentRecordList }
    }
}
