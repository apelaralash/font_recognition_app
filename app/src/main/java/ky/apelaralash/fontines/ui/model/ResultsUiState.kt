package ky.apelaralash.fontines.ui.model

import ky.apelaralash.fontines.domain.model.FontMatch

/**
 * Состояния UI экрана результатов распознавания
 */
sealed class ResultsUiState {
    object Idle : ResultsUiState()
    object Loading : ResultsUiState()
    data class Success(val fonts: List<FontMatch>) : ResultsUiState()
    data class Error(val message: String) : ResultsUiState()
}
