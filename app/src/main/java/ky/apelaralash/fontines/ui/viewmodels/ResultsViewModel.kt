package ky.apelaralash.fontines.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import ky.apelaralash.fontines.domain.interactor.FontInteractor
import ky.apelaralash.fontines.domain.model.FontMatch
import ky.apelaralash.fontines.ui.model.ResultsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана результатов распознавания
 * Получает данные через Navigation Arguments
 */
@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<ResultsUiState>(ResultsUiState.Idle)
    val uiState: StateFlow<ResultsUiState> = _uiState.asStateFlow()

    private var fontsList: List<FontMatch> = emptyList()

    init {
        val fontsJson = savedStateHandle.get<List<FontMatch>>("fonts")
        if (fontsJson != null) {
            loadResults(fontsJson)
        }
    }

    /**
     * Загрузка результатов распознавания
     * @param recognitionResults результаты распознавания
     */
    private fun loadResults(recognitionResults: List<FontMatch>) {
        viewModelScope.launch {
            _uiState.value = ResultsUiState.Loading

            try {
                fontsList = recognitionResults
                _uiState.value = ResultsUiState.Success(fontsList)
            } catch (e: Exception) {
                _uiState.value = ResultsUiState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    /**
     * Выбор шрифта для просмотра деталей
     * @param fontId идентификатор шрифта
     */
    fun selectFont(fontId: String) {
        fontsList.find { it.id == fontId }
            ?.let { font ->
                _uiState.value = ResultsUiState.FontSelected(font)
            }
    }

    /**
     * Сброс выбора шрифта
     */
    fun clearSelection() {
        _uiState.value = ResultsUiState.Idle
    }

    /**
     * Получение текущих результатов
     */
    fun getFonts(): List<FontMatch> = fontsList
}