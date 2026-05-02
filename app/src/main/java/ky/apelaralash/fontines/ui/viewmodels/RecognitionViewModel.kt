package ky.apelaralash.fontines.ui.viewmodels

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import ky.apelaralash.fontines.domain.interactor.FontInteractor
import ky.apelaralash.fontines.domain.model.FontMatch
import ky.apelaralash.fontines.ui.model.RecognitionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ky.apelaralash.fontines.ui.navigation.FontMatchArguments
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.FileNotFoundException
import javax.inject.Inject

/**
 * ViewModel для экрана распознавания шрифта
 * Использует существующий FontInteractor с методом recognize()
 */
@HiltViewModel
class RecognitionViewModel @Inject constructor(
    private val fontInteractor: FontInteractor,
    private val gson: Gson,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow<RecognitionUiState>(RecognitionUiState.Idle)
    val uiState: StateFlow<RecognitionUiState> = _uiState.asStateFlow()

    init {
        val imageUri = savedStateHandle.get<String>("imageUri")
        if (imageUri != null) {
            recognizeFont(imageUri.toUri())
        }
    }

    /**
     * Запуск процесса распознавания шрифта
     * @param imageUri URI изображения
     */
    private fun recognizeFont(imageUri: Uri) {
        viewModelScope.launch {
            _uiState.value = RecognitionUiState.Loading(0f, "Подготовка изображения...")

            try {
                // Симуляция прогресса
                simulateProgress()

                // Вызов существующего метода interactor.recognize()
                val fonts = fontInteractor.recognize(imageUri)
                val fontsJson = gson.toJson(
                    FontMatchArguments(fonts)
                )

                _uiState.value = RecognitionUiState.Success(fontsJson)
            } catch (e: Exception) {
                _uiState.value = RecognitionUiState.Error(e.message ?: "Ошибка распознавания")
            }
        }
    }

    private suspend fun simulateProgress() {
        val steps = listOf(
            0.2f to "Загрузка изображения...",
            0.4f to "Анализ текста...",
            0.6f to "Поиск шрифтов в базе...",
            0.8f to "Сравнение характеристик...",
            1.0f to "Завершение..."
        )

        var currentProgress = 0f
        for ((targetProgress, status) in steps) {
            _uiState.value = RecognitionUiState.Loading(targetProgress, status)
            while (currentProgress < targetProgress) {
                currentProgress += 0.05f
                _uiState.value = RecognitionUiState.Loading(currentProgress.coerceAtMost(targetProgress), status)
                kotlinx.coroutines.delay(100)
            }
        }
    }

    /**
     * Сброс состояния
     */
    fun reset() {
        _uiState.value = RecognitionUiState.Idle
    }

    private fun createMultipartPart(context: Context, imageUri: Uri): MultipartBody.Part {
        // Получаем имя файла
        val fileName = getFileName(context, imageUri)

        // Открываем InputStream через ContentResolver
        val inputStream = context.contentResolver.openInputStream(imageUri)
            ?: throw FileNotFoundException("Не удалось открыть URI: $imageUri")

        // Создаём RequestBody из потока
        val requestBody = inputStream.use { stream ->
            val content = stream.readBytes()
            content.toRequestBody(
                "image/*".toMediaTypeOrNull() ?: "image/jpeg".toMediaType(),
                0,
                content.size
            )
        }

        // Создаём MultipartPart
        return MultipartBody.Part.createFormData(
            "file", // имя поля в multipart
            fileName,
            requestBody
        )
    }

    private fun getFileName(context: Context, uri: Uri): String {
        return context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            cursor.getString(nameIndex)
        } ?: "image.jpg"
    }
}