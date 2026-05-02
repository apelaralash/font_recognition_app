package ky.apelaralash.fontines.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FontMatch(
    val id: String,
    val name: String,
    val similarity: Float, // 0.0 - 1.0
    val designer: String? = null,
    val year: Int? = null,
    val category: String? = null,
    val description: String? = null,
    val license: String? = null,
    val downloadUrl: String? = null,
    val previewUrl: String? = null
) {
    val similarityPercent: Int
        get() = (similarity * 100).toInt()
}
