package ky.apelaralash.fontines.domain.interactor

import ky.apelaralash.fontines.domain.model.FontMatch
import ky.apelaralash.fontines.domain.repository.FontRepository
import javax.inject.Inject

internal class FontInteractor @Inject constructor(
    private val repository: FontRepository
) {

    suspend fun recognize(uri: String): List<FontMatch> {
        return repository.recognizeFont(uri)
    }
}