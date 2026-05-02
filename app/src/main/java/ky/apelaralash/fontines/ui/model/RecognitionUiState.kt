package ky.apelaralash.fontines.ui.model

import ky.apelaralash.fontines.domain.model.FontMatch

/**
 * Состояния UI экрана распознавания
 */
sealed class RecognitionUiState {
    object Idle : RecognitionUiState()
    data class Loading(val progress: Float, val status: String) : RecognitionUiState()
    data class Success(val fontsJson: String) : RecognitionUiState()
    data class Error(val message: String) : RecognitionUiState()
}