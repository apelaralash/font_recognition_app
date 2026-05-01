package ky.apelaralash.fontines.ui.model

import ky.apelaralash.fontines.domain.model.FontMatch

/**
 * Состояния UI экрана деталей шрифта
 */
sealed class FontDetailUiState {
    object Idle : FontDetailUiState()
    object Loading : FontDetailUiState()
    data class Success(val font: FontMatch) : FontDetailUiState()
    data class Error(val message: String) : FontDetailUiState()
}