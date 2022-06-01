package ercanduman.android.audiorecorder.data.model

data class Record(
    val name: String,
    val path: String,
    val startedTime: Long = 0L,
    val stoppedTime: Long = 0L
) {
    val duration = (stoppedTime - startedTime) / 1000L
}
