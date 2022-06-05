package ercanduman.android.audiorecorder.data.source

import android.media.MediaPlayer
import ercanduman.android.audiorecorder.data.model.Record
import ercanduman.android.audiorecorder.internal.util.Logger

class PlayingDataSourceImpl(
    private val mediaPlayer: MediaPlayer
) : PlayingDataSource {

    override fun startPlaying(record: Record) {
        mediaPlayer.apply {
            try {
                if (!isPlaying) {
                    setDataSource(record.path)
                    prepare()
                    start()
                }
            } catch (e: Throwable) {
                Logger.log("prepare() failed. $e")
            }
        }
    }

    override fun stopPlaying() {
        mediaPlayer.apply {
            stop()
            reset()
        }
    }
}
