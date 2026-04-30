package ky.apelaralash.fontines.data.api

import ky.apelaralash.fontines.data.model.FontMatch
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * API интерфейс для сервиса распознавания шрифтов
 */
interface FontApiService {
    
    /**
     * Отправка изображения для распознавания шрифта
     * @param image изображение шрифта в формате multipart
     * @return список найденных похожих шрифтов
     */
    @Multipart
    @POST("api/v1/recognize")
    suspend fun recognizeFont(
        @Part image: okhttp3.MultipartBody.Part
    ): List<FontMatch>
    
    /**
     * Получение информации о конкретном шрифте
     * @param fontId идентификатор шрифта
     * @return информация о шрифте
     */
    // @GET("api/v1/fonts/{fontId}")
    // suspend fun getFontById(@Path("fontId") fontId: String): FontMatch
}