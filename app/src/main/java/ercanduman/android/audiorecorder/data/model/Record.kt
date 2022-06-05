package ercanduman.android.audiorecorder.data.model

import java.util.concurrent.TimeUnit

data class Record(
    val name: String,
    val path: String,
    val startedTime: Long = System.currentTimeMillis(),
    val stoppedTime: Long = System.currentTimeMillis()
) {
    private val recodingTime = stoppedTime - startedTime
    private val recodingTimeInMin = TimeUnit.MILLISECONDS.toMinutes(recodingTime)
    val duration = String.format(
        "%02d:%02d",
        recodingTimeInMin,
        TimeUnit.MILLISECONDS.toSeconds(recodingTime) - TimeUnit.MINUTES.toSeconds(recodingTimeInMin)
    )
}
