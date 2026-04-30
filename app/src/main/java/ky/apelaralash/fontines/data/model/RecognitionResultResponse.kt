package ky.apelaralash.fontines.data.model

import com.google.gson.annotations.SerializedName

data class RecognitionResultResponse(
    @SerializedName("detected_font")
    val detectedFont: String,
    @SerializedName("confidence")
    val confidence: Float,
    @SerializedName("additional_info")
    val additionalInfo: FontInfoResponse
)
