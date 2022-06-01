package ercanduman.android.audiorecorder.data.repository

import ercanduman.android.audiorecorder.data.source.PlayingDataSource
import ercanduman.android.audiorecorder.data.source.RecordingDataSource

class RecordsRepository(
    private val playingDataSource: PlayingDataSource,
    private val recordingDataSource: RecordingDataSource
) : PlayingDataSource by playingDataSource,
    RecordingDataSource by recordingDataSource