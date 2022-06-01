package ercanduman.android.audiorecorder.data.source

import ercanduman.android.audiorecorder.data.model.Record

interface PlayingDataSource {
    fun startPlaying(record: Record)
    fun stopPlaying()
}
