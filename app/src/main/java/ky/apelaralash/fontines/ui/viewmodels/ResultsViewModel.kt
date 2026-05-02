package ky.apelaralash.fontines.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import ky.apelaralash.fontines.domain.model.FontMatch
import ky.apelaralash.fontines.ui.model.ResultsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ky.apelaralash.fontines.ui.navigation.FontMatchArguments
import javax.inject.Inject

/**
 * ViewModel для экрана результатов распознавания
 * Получает данные через Navigation Arguments
 */
@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val gson: Gson,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<ResultsUiState>(ResultsUiState.Idle)
    val uiState: StateFlow<ResultsUiState> = _uiState.asStateFlow()

    private var fontsList: List<FontMatch> = emptyList()

    init {
        val fontsJson = savedStateHandle.get<String>("fontsJson")
        if (fontsJson != null) {
            loadResults(fontsJson)
        }
    }

    /**
     * Загрузка результатов распознавания
     * @param recognitionResults результаты распознавания
     */
    private fun loadResults(recognitionResults: String) {
        viewModelScope.launch {
            _uiState.value = ResultsUiState.Loading

            try {
                val args = gson.fromJson(recognitionResults, FontMatchArguments::class.java)
                fontsList = args.fonts
                _uiState.value = ResultsUiState.Success(fontsList)
            } catch (e: Exception) {
                _uiState.value = ResultsUiState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun createFontJson(font: FontMatch): String {
        return gson.toJson(font)
    }
}