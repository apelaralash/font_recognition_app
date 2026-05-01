package ky.apelaralash.fontines.ui.navigation

import kotlinx.serialization.Serializable
import ky.apelaralash.fontines.domain.model.FontMatch

@Serializable
data class FontMatchArguments(
    val fonts: List<FontMatch>
): java.io.Serializable