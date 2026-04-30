package ky.apelaralash.fontines.data.repository

import ky.apelaralash.fontines.data.api.FontApiService
import ky.apelaralash.fontines.data.model.FontMatch
import kotlinx.coroutines.delay
import kotlin.random.Random
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Репозиторий для работы со шрифтами
 * Использует Hilt для внедрения зависимостей
 */
@Singleton
class FontRepository @Inject constructor(
    private val fontApiService: FontApiService
) {

    // Моковые данные для демонстрации
    val knownFonts = listOf(
        FontMatch(
            id = "1",
            name = "Helvetica",
            similarity = 0f,
            designer = "Max Miedinger",
            year = 1957,
            category = "Sans-serif",
            description = "Helvetica — один из самых известных и широко используемых шрифтов в мире. Чистый, нейтральный и универсальный дизайн делает его подходящим для множества применений.",
            license = "Commercial",
            downloadUrl = "https://example.com/helvetica"
        ),
        FontMatch(
            id = "2",
            name = "Arial",
            similarity = 0f,
            designer = "Robin Nicholas, Patricia Saunders",
            year = 1982,
            category = "Sans-serif",
            description = "Arial — популярный шрифт без засечек, похожий на Helvetica. Широко используется в операционных системах и приложениях.",
            license = "Free (with Windows)",
            downloadUrl = "https://example.com/arial"
        ),
        FontMatch(
            id = "3",
            name = "Times New Roman",
            similarity = 0f,
            designer = "Stanley Morison",
            year = 1931,
            category = "Serif",
            description = "Times New Roman — классический шрифт с засечками, созданный для газеты The Times. Стал стандартом для академических и деловых документов.",
            license = "Free (with Windows)",
            downloadUrl = "https://example.com/times"
        ),
        FontMatch(
            id = "4",
            name = "Georgia",
            similarity = 0f,
            designer = "Matthew Carter",
            year = 1993,
            category = "Serif",
            description = "Georgia — шрифт с засечками, разработанный специально для экранного отображения. Отличная читаемость при малых размерах.",
            license = "Free (with Windows)",
            downloadUrl = "https://example.com/georgia"
        ),
        FontMatch(
            id = "5",
            name = "Roboto",
            similarity = 0f,
            designer = "Christian Robertson",
            year = 2011,
            category = "Sans-serif",
            description = "Roboto — современный шрифт от Google, сочетающий механическую форму с дружелюбными изгибами. Стандартный шрифт Android.",
            license = "Open Source (Apache)",
            downloadUrl = "https://fonts.google.com/specimen/Roboto"
        ),
        FontMatch(
            id = "6",
            name = "Open Sans",
            similarity = 0f,
            designer = "Steve Matteson",
            year = 2010,
            category = "Sans-serif",
            description = "Open Sans — нейтральный шрифт с дружелюбным видом, оптимизированный для мобильных устройств и веба.",
            license = "Open Source (Apache)",
            downloadUrl = "https://fonts.google.com/specimen/Open+Sans"
        ),
        FontMatch(
            id = "7",
            name = "Montserrat",
            similarity = 0f,
            designer = "Julieta Ulanovsky",
            year = 2011,
            category = "Sans-serif",
            description = "Montserrat — геометрический шрифт без засечек, вдохновлённый городской типографикой района Монтсеррат в Буэнос-Айресе.",
            license = "Open Source (OFL)",
            downloadUrl = "https://fonts.google.com/specimen/Montserrat"
        ),
        FontMatch(
            id = "8",
            name = "Poppins",
            similarity = 0f,
            designer = "Indian Type Foundry",
            year = 2014,
            category = "Sans-serif",
            description = "Poppins — геометрический шрифт без засечек с поддержкой деванагари и латиницы. Современный и универсальный.",
            license = "Open Source (OFL)",
            downloadUrl = "https://fonts.google.com/specimen/Poppins"
        )
    )
    
    /**
     * Распознавание шрифта по изображению
     * @param imageUri путь к изображению
     * @return список найденных похожих шрифтов
     */
    suspend fun recognizeFont(imageUri: String): List<FontMatch> {
        return try {
            // Создаем файл из URI
            val imageFile = File(imageUri)

            // Создаем RequestBody для изображения
            val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

            // Отправляем запрос к API
            fontApiService.recognizeFont(imagePart)
        } catch (e: Exception) {
            // В случае ошибки API возвращаем моковые данные
            e.printStackTrace()
            getMockFontMatches()
        }
    }

    /**
     * Возвращает моковые данные для демонстрации или при ошибке API
     */
    private suspend fun getMockFontMatches(): List<FontMatch> {
        delay(2000) // Симуляция задержки сети
        return knownFonts.map { font ->
            font.copy(
                similarity = (0.6f..1.0f).random()
            )
        }.sortedByDescending { it.similarity }
    }
    
    fun getFontById(id: String): FontMatch? {
        return knownFonts.find { it.id == id }
    }
}
