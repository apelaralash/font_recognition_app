package ky.apelaralash.fontines.data.mapper

import android.R.attr.description
import android.R.attr.name
import ky.apelaralash.fontines.data.errors.CustomError
import ky.apelaralash.fontines.data.model.FontRecognitionResponse
import ky.apelaralash.fontines.domain.model.FontMatch
import javax.inject.Inject

class FontResponseMapper @Inject constructor() {

    fun map(response: FontRecognitionResponse): List<FontMatch> {
        return response.results.map {
            with(it.additionalInfo) {
                FontMatch(
                    id = fontId,
                    name = name,
                    similarity = it.confidence,
                    license = licenseInfo,
                    designer = creatorName,
                    downloadUrl = downloadUrl,
                    description = description,
                    category = style,
                )
            }
        }
    }
}