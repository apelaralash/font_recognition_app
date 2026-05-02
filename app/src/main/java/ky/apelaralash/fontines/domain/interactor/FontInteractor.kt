package ky.apelaralash.fontines.domain.interactor

import android.net.Uri
import ky.apelaralash.fontines.domain.model.FontMatch
import ky.apelaralash.fontines.domain.repository.FontRepository
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class FontInteractor @Inject constructor(
    private val repository: FontRepository
) {

    suspend fun recognize(part: MultipartBody.Part): List<FontMatch> {
        return repository.recognizeFont(part)
    }
}