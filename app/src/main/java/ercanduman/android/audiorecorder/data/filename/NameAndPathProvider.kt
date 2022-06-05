package ercanduman.android.audiorecorder.data.filename

interface NameAndPathProvider {
    fun provideName(audioId: Int): String
    fun provideFilePath(audioId: Int): String
}