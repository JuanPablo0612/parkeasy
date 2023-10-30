package com.parkeasy.android.data.places.model

import com.google.gson.annotations.SerializedName

/**
 * CountryResponse class.
 */
data class CountryResponse(
    @SerializedName("country_name") val name: String,
    @SerializedName("country_short_name") val shortName: String
)
