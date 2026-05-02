package ky.apelaralash.fontines.domain.interactor

import android.content.Context
import android.net.Uri
import ky.apelaralash.fontines.domain.model.FontMatch
import ky.apelaralash.fontines.domain.repository.FontRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileNotFoundException
import javax.inject.Inject

class FontInteractor @Inject constructor(
    private val repository: FontRepository
) {

    // Добавьте этот метод:
    suspend fun recognize(imageUri: Uri, context: Context): List<FontMatch> {
        val part = createMultipartPart(context, imageUri)
        return repository.recognizeFont(part)
    }

    // Добавьте вспомогательный метод:
    private fun createMultipartPart(context: Context, imageUri: Uri): MultipartBody.Part {
        val inputStream = context.contentResolver.openInputStream(imageUri)
            ?: throw FileNotFoundException("Не удалось открыть изображение")

        val mimeType = context.contentResolver.getType(imageUri) ?: "image/jpeg"
        val mediaType = mimeType.toMediaTypeOrNull() ?: "image/jpeg".toMediaType()

        val bytes = inputStream.use { it.readBytes() }
        val requestBody = bytes.toRequestBody(mediaType, 0, bytes.size)

        return MultipartBody.Part.createFormData(
            "image",
            "image_${System.currentTimeMillis()}.jpg",
            requestBody
        )
    }
}