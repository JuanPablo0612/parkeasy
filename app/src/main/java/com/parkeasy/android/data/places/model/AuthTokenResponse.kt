package com.parkeasy.android.data.places.model

import com.google.gson.annotations.SerializedName

data class AuthTokenResponse(
    @SerializedName("auth_token") val authToken: String
)
