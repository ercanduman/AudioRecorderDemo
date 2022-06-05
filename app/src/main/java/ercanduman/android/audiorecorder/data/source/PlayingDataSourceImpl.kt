package ercanduman.android.audiorecorder.data.source

import android.media.MediaPlayer
import ercanduman.android.audiorecorder.internal.util.Logger

class PlayingDataSourceImpl(
    private val mediaPlayer: MediaPlayer
) : PlayingDataSource {

    override fun startPlaying(filePath: String) {
        mediaPlayer.apply {
            try {
                if (!isPlaying) {
                    setDataSource(filePath)
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
            if (isPlaying) {
                stop()
                reset()
            }
        }
    }
}
