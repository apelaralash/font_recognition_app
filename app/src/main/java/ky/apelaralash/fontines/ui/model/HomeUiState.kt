package ky.apelaralash.fontines.ui.model

import android.net.Uri

sealed class HomeUiState {
    object Idle : HomeUiState()
    data class ImageSelected(val imageUri: Uri) : HomeUiState()
}