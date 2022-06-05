package ercanduman.android.audiorecorder.di

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ercanduman.android.audiorecorder.data.filename.NameAndPathProvider
import ercanduman.android.audiorecorder.data.filename.NameAndPathProviderImpl
import ercanduman.android.audiorecorder.data.repository.RecordsRepository
import ercanduman.android.audiorecorder.data.source.PlayingDataSource
import ercanduman.android.audiorecorder.data.source.PlayingDataSourceImpl
import ercanduman.android.audiorecorder.data.source.RecordingDataSource
import ercanduman.android.audiorecorder.data.source.RecordingDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMediaPlayer(): MediaPlayer {
        return MediaPlayer()
    }

    @Provides
    fun providePlayingDataSource(mediaPlayer: MediaPlayer): PlayingDataSource {
        return PlayingDataSourceImpl(mediaPlayer)
    }

    @Provides
    fun provideMediaRecorder(): MediaRecorder {
        @Suppress("DEPRECATION")
        return MediaRecorder()
    }

    @Provides
    fun provideNameAndPathProvider(
        @ApplicationContext context: Context
    ): NameAndPathProvider {
        return NameAndPathProviderImpl(context)
    }

    @Provides
    fun provideRecordingDataSource(
        mediaRecorder: MediaRecorder,
        nameAndPathProvider: NameAndPathProvider
    ): RecordingDataSource {
        return RecordingDataSourceImpl(mediaRecorder, nameAndPathProvider)
    }

    @Provides
    @Singleton
    fun provideRecordsRepository(
        playingDataSource: PlayingDataSource,
        recordingDataSource: RecordingDataSource
    ): RecordsRepository {
        return RecordsRepository(playingDataSource, recordingDataSource)
    }
}
