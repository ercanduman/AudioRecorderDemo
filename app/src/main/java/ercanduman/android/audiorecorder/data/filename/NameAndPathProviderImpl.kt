package ercanduman.android.audiorecorder.data.filename

import android.content.Context
import java.io.File
import javax.inject.Inject

class NameAndPathProviderImpl @Inject constructor(
    private val context: Context
) : NameAndPathProvider {

    override fun provideName(audioId: Int): String {
        return "audiorecordtest$audioId.mp3"
    }

    override fun provideFilePath(audioId: Int): String {
        val externalDirectory = context.externalCacheDir?.absolutePath
        val directory = "$externalDirectory/AudioRecorderDemo"
        createDirectoryIfNotExist(directory)
        return "$directory/${provideName(audioId)}"
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