package ercanduman.android.audiorecorder.data.filename

import android.content.Context
import java.io.File
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class NameAndPathProviderImpl @Inject constructor(
    private val context: Context
) : NameAndPathProvider {


    private val atomicInteger = AtomicInteger(0)
    private val currentName = "audiorecordtest${atomicInteger.getAndIncrement()}.mp3"

    override fun provideName(): String {
        return currentName
    }

    override fun provideFilePath(): String {
        val externalDirectory = context.externalCacheDir?.absolutePath
        val directory = "$externalDirectory/AudioRecorderDemo/"
        createDirectoryIfNotExist(directory)
        return "$directory/$currentName"
    }

    private fun createDirectoryIfNotExist(directory: String) {
        try {
            val path = File(directory)
            if (path.isDirectory || !path.exists()) {
                path.mkdir()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}