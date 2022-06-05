package ercanduman.android.audiorecorder.data.model

data class Record(
    val name: String,
    val path: String,
    val startedTime: Long = System.currentTimeMillis(),
    val stoppedTime: Long = System.currentTimeMillis()
) {
    val duration = (stoppedTime - startedTime) / 1000L
}
