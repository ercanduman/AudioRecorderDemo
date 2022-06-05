package ercanduman.android.audiorecorder.internal.util

import android.util.Log
import ercanduman.android.audiorecorder.BuildConfig

object Logger {
    private const val TAG = "AudioRecorder"

    fun log(message: String) {
        if (BuildConfig.DEBUG) Log.d(TAG, message)
    }
}
