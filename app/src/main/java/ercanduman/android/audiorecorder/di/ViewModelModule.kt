package ercanduman.android.audiorecorder.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ercanduman.android.audiorecorder.ui.main.recording.delegate.UIStateHandler
import ercanduman.android.audiorecorder.ui.main.recording.delegate.UIStateHandlerDelegate

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideUiStateHandler(): UIStateHandler {
        return UIStateHandlerDelegate()
    }
}