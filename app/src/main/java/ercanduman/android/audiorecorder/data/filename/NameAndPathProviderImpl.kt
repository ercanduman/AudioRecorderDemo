package ercanduman.android.audiorecorder.data.filename

import android.os.Environment
import java.util.concurrent.atomic.AtomicInteger

class NameAndPathProviderImpl : NameAndPathProvider {

    @Suppress("DEPRECATION")
    private val externalDirectory = Environment.getExternalStorageDirectory().absolutePath

    private val atomicInteger = AtomicInteger(0)
    private val currentName = "audiorecordtest${atomicInteger.getAndIncrement()}.mp3"

    override fun provideName(): String {
        return currentName
    }

    override fun provideFilePath(): String {
        return "$externalDirectory/$currentName"
    }
}