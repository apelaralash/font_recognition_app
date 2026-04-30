package ky.apelaralash.fontines.data.repository

import ky.apelaralash.fontines.data.api.FontApiService
import ky.apelaralash.fontines.data.mapper.FontResponseMapper
import ky.apelaralash.fontines.domain.model.FontMatch
import ky.apelaralash.fontines.domain.repository.FontRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

/**
 * Репозиторий для работы со шрифтами
 * Использует Hilt для внедрения зависимостей
 */
internal class FontRepositoryImpl @Inject constructor(
    private val fontApiService: FontApiService,
    private val mapper: FontResponseMapper,
) : FontRepository {
    
    /**
     * Распознавание шрифта по изображению
     * @param imageUri путь к изображению
     * @return список найденных похожих шрифтов
     */
    override suspend fun recognizeFont(imageUri: String): List<FontMatch> {
        return try {
            // Создаем файл из URI
            val imageFile = File(imageUri)

            // Создаем RequestBody для изображения
            val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

            // Отправляем запрос к API
            val font = mapper.map(fontApiService.recognizeFont(imagePart))
            listOf(font)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}
