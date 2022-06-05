package ercanduman.android.audiorecorder.ui.main.recording.delegate

import java.util.UUID

data class SnackbarMessage(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val message: String = ""
)