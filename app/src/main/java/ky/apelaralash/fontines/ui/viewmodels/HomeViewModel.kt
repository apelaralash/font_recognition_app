package ky.apelaralash.fontines.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ky.apelaralash.fontines.ui.model.HomeUiState
import javax.inject.Inject

/**
 * ViewModel для главного экрана
 * Управляет выбором изображения
 */
@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    /**
     * Обработка выбора изображения
     * @param imageUri URI выбранного изображения
     */
    fun onImageSelected(imageUri: Uri) {
        _uiState.value = HomeUiState.ImageSelected(imageUri)
    }

    /**
     * Сброс состояния после перехода к распознаванию
     */
    fun reset() {
        _uiState.value = HomeUiState.Idle
    }
}