package ky.apelaralash.fontines.data.mapper

import ky.apelaralash.fontines.data.errors.CustomError
import ky.apelaralash.fontines.data.model.FontRecognitionResponse
import ky.apelaralash.fontines.domain.model.FontMatch
import javax.inject.Inject

internal class FontResponseMapper @Inject constructor() {

    fun map(response: FontRecognitionResponse): FontMatch {
        return if (response.result != null) {
            with (response.result.additionalInfo) {
                FontMatch(
                    id = fontId,
                    name = name,
                    similarity = response.result.confidence,
                    license = licenseInfo,
                    designer = creatorName,
                    downloadUrl = downloadUrl,
                    description = description,
                    category = style,
                )
            }
        } else {
            throw CustomError()
        }
    }
}