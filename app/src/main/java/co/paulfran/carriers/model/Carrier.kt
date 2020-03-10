package co.paulfran.carriers.model

import com.google.gson.annotations.SerializedName

data class Carrier(
    @SerializedName("name")
    val carrierName: String?,
    @SerializedName("ubi")
    val carrierUbi: String?,
    val photo: String?
)