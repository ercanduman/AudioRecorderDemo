package ercanduman.android.audiorecorder.data.source

import android.media.MediaRecorder
import android.util.Log
import ercanduman.android.audiorecorder.data.filename.NameAndPathProvider
import ercanduman.android.audiorecorder.data.model.Record
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.atomic.AtomicInteger

private const val TAG = "RecordingDataSourceImpl"

class RecordingDataSourceImpl(
    private val recorder: MediaRecorder,
    private val nameAndPathProvider: NameAndPathProvider
) : RecordingDataSource {
    private val atomicInteger = AtomicInteger(0)

    private var currentRecord: Record? = null

    private val currentRecordList = mutableListOf<Record>()

    override val records: Flow<List<Record>> = flow { emit(currentRecordList) }
    override fun startRecording() {
        recorder.apply {
            val audioId = atomicInteger.incrementAndGet()
            currentRecord = Record(
                nameAndPathProvider.provideName(audioId),
                nameAndPathProvider.provideFilePath(audioId)
            )

            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(currentRecord?.path)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: Throwable) {
                e.printStackTrace()
                Log.e(TAG, "prepare() failed. $e")
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
            reset()   // You can reuse the object by going back to setAudioSource() step
            // release() // Now the object cannot be reused
        }

        currentRecord?.let { insertRecord(it.copy(stoppedTime = System.currentTimeMillis())) }
    }

    override fun insertRecord(record: Record) {
        currentRecordList.add(record)
        Log.d(TAG, "insertRecord: $record")
    }

    override fun deleteRecord(record: Record) {
        currentRecordList.remove(record)
    }
}
