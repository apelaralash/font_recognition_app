package ky.apelaralash.fontines.domain.repository

import android.net.Uri
import ky.apelaralash.fontines.domain.model.FontMatch
import okhttp3.MultipartBody

interface FontRepository {

    suspend fun recognizeFont(part: MultipartBody.Part): List<FontMatch>
}