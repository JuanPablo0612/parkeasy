package com.parkeasy.android.data.places.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * FlagsApiService interface.
 */
interface FlagsApiService {
    /**
     * Retrieves the flag of a country based on its country code.
     *
     * @param countryCode The country code of the desired country's flag.
     * @return A [Call] object that represents the asynchronous request for the flag.
     */
    @GET("{countryCode}.svg")
    fun getFlagByCountryCode(@Path("countryCode") countryCode: String): Call<String>
}