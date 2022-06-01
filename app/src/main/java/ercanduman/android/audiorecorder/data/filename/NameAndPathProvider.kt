package ercanduman.android.audiorecorder.data.filename

interface NameAndPathProvider {
    fun provideName(): String
    fun provideFilePath(): String
}