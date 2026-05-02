package ky.apelaralash.fontines.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import ky.apelaralash.fontines.ui.model.FontDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ky.apelaralash.fontines.domain.model.FontMatch
import javax.inject.Inject

/**
 * ViewModel для экрана детальной информации о шрифте
 */
@HiltViewModel
class FontDetailViewModel @Inject constructor(
    private val gson: Gson,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<FontDetailUiState>(FontDetailUiState.Idle)
    val uiState: StateFlow<FontDetailUiState> = _uiState.asStateFlow()

    init {
        val fontJson = savedStateHandle.get<String>("fontJson")
        if (fontJson != null) {
            setFont(fontJson)
        }
    }

    /**
     * Установка шрифта напрямую (например, из навигации)
     * @param fontJson объект шрифта
     */
    private fun setFont(fontJson: String) {
        val font = gson.fromJson(fontJson, FontMatch::class.java)
        _uiState.value = FontDetailUiState.Success(font)
    }

    /**
     * Сброс состояния
     */
    fun reset() {
        _uiState.value = FontDetailUiState.Idle
    }
}