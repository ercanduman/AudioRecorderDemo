package ercanduman.android.audiorecorder.data.source

import android.media.MediaPlayer
import android.util.Log
import ercanduman.android.audiorecorder.data.model.Record

private const val TAG = "PlayingDataSourceImpl"

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
                e.printStackTrace()
                Log.e(TAG, "prepare() failed. $e")
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
