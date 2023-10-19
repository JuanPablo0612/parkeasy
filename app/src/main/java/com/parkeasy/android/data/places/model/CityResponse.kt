package com.parkeasy.android.data.places.model

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("city_name") val name: String
)
