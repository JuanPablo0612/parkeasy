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

/**
 * PlacesApiService interface.
 */
interface PlacesApiService {
    /**
     * Retrieves the authentication token from the API server.
     *
     * @return A [Call] object representing the asynchronous request for the authentication token.
     *         The response will be of type [AuthTokenResponse].
     *
     * @headers The following headers are required for this request:
     *          - "api-token": The API token used for authentication. Example: "eRyNT9fXyaU8TzCRHfjsaW6ft5c2QwVhmAxGi0GCk3MGDPEPU3anQNLd1zwG42CMWVg"
     *          - "user-email": The email address of the user. Example: "gallegojuanpablo39@gmail.com"
     */
    @Headers(
        "api-token: eRyNT9fXyaU8TzCRHfjsaW6ft5c2QwVhmAxGi0GCk3MGDPEPU3anQNLd1zwG42CMWVg",
        "user-email: gallegojuanpablo39@gmail.com"
    )
    @GET("getaccesstoken")
    fun getAuthToken(): Call<AuthTokenResponse>

    /**
     * Retrieves a list of all countries.
     *
     * @param auth The authorization token for authentication.
     * @return A [Call] object with a [List] of [CountryResponse] as the response type.
     */
    @GET("countries")
    fun getAllCountries(@Header("Authorization") auth: String): Call<List<CountryResponse>>

    /**
     * Retrieves a list of all states in a specific country.
     *
     * @param countryName The name of the country.
     * @param auth The authorization token for authentication.
     * @return A [Call] object with a [List] of [StateResponse] as the response type.
     */
    @GET("states/{countryName}")
    fun getAllStatesByCountry(
        @Path("countryName") countryName: String,
        @Header("Authorization") auth: String
    ): Call<List<StateResponse>>

    /**
     * Retrieves a list of all cities in a specific state.
     *
     * @param stateName The name of the state to retrieve cities from.
     * @param auth The authorization token for accessing the API.
     * @return A [Call] object with a [List] of [CityResponse] as the response type.
     */
    @GET("cities/{stateName}")
    fun getAllCitiesByState(
        @Path("stateName") stateName: String,
        @Header("Authorization") auth: String
    ): Call<List<CityResponse>>
}