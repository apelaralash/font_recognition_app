package ky.apelaralash.fontines.data.model
import com.google.gson.annotations.SerializedName

data class FontRecognitionResponse(
    @SerializedName("request_id")
    val requestId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("result")
    val result: RecognitionResultResponse? = null,
    @SerializedName("error")
    val error: Map<String, String>? = null,
    @SerializedName("processed_at")
    val processedAt: String
)
