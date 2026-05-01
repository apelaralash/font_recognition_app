package ky.apelaralash.fontines.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ky.apelaralash.fontines.domain.interactor.FontInteractor
import ky.apelaralash.fontines.domain.model.FontMatch
import ky.apelaralash.fontines.ui.model.FontDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана детальной информации о шрифте
 */
@HiltViewModel
class FontDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<FontDetailUiState>(FontDetailUiState.Idle)
    val uiState: StateFlow<FontDetailUiState> = _uiState.asStateFlow()

    init {
        val font = savedStateHandle.get<FontMatch>("font")
        if (font != null) {
            setFont(font)
        }
    }

    /**
     * Установка шрифта напрямую (например, из навигации)
     * @param font объект шрифта
     */
    private fun setFont(font: FontMatch) {
        _uiState.value = FontDetailUiState.Success(font)
    }

    /**
     * Сброс состояния
     */
    fun reset() {
        _uiState.value = FontDetailUiState.Idle
    }
}