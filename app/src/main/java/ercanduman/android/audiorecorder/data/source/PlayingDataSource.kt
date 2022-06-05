package ercanduman.android.audiorecorder.data.source

interface PlayingDataSource {
    fun startPlaying(filePath: String)
    fun stopPlaying()
}
