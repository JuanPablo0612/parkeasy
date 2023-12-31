package com.parkeasy.android.data.places.model

import com.google.gson.annotations.SerializedName

/**
 * StateResponse class.
 */
data class StateResponse(
    @SerializedName("state_name") val name: String
)
