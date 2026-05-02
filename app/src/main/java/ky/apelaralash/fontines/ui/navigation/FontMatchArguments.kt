package ky.apelaralash.fontines.ui.navigation

import ky.apelaralash.fontines.domain.model.FontMatch
import kotlinx.serialization.Serializable

@Serializable
data class FontMatchArguments(
    val fonts: List<FontMatch>
)