package ky.apelaralash.fontines.ui.provider

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.IOException
import javax.inject.Inject

class FileProvider @Inject constructor() {

    fun createTempImageUri(context: Context): Uri {
        return try {
            val file = File.createTempFile(
                "temp_image_${System.currentTimeMillis()}",
                ".jpg",
                context.cacheDir
            )
            androidx.core.content.FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
        } catch (e: IOException) {
            throw RuntimeException("Не удалось создать временный файл", e)
        }
    }
}