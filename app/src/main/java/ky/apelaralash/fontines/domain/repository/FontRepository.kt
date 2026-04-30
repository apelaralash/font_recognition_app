package ky.apelaralash.fontines.domain.repository

import ky.apelaralash.fontines.domain.model.FontMatch

internal interface FontRepository {

    suspend fun recognizeFont(imageUri: String): List<FontMatch>
}