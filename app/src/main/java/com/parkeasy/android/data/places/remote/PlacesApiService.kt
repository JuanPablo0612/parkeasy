package com.parkeasy.android.data.places.remote

import com.parkeasy.android.data.places.model.AuthTokenResponse
import com.parkeasy.android.data.places.model.CityResponse
import com.parkeasy.android.data.places.model.CountryResponse
import com.parkeasy.android.data.places.model.StateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface PlacesApiService {
    @Headers("api-token: eRyNT9fXyaU8TzCRHfjsaW6ft5c2QwVhmAxGi0GCk3MGDPEPU3anQNLd1zwG42CMWVg", "user-email: gallegojuanpablo39@gmail.com")
    @GET("getaccesstoken")
    fun getAuthToken(): Call<AuthTokenResponse>

    @GET("countries")
    fun getAllCountries(@Header("Authorization") auth: String): Call<List<CountryResponse>>

    @GET("states/{countryName}")
    fun getAllStatesByCountry(@Path("countryName") countryName: String, @Header("Authorization") auth: String): Call<List<StateResponse>>

    @GET("cities/{stateName}")
    fun getAllCitiesByState(@Path("stateName") stateName: String, @Header("Authorization") auth: String): Call<List<CityResponse>>
}