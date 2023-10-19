package com.parkeasy.android.data.places.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FlagsApiService {
    @GET("{countryCode}.svg")
    fun getFlagByCountryCode(@Path("countryCode") countryCode: String): Call<String>
}