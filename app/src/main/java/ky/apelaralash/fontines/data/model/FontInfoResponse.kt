package ky.apelaralash.fontines.data.model

import com.google.gson.annotations.SerializedName

data class FontInfoResponse(
    @SerializedName("font_id")
    val fontId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("style")
    val style: String? = null,
    @SerializedName("license")
    val licenseInfo: String? = null,
    @SerializedName("download_url")
    val downloadUrl: String? = null,
    @SerializedName("creator_name")
    val creatorName: String? = null
)