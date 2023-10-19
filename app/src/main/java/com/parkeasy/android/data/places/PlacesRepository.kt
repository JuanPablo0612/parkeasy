package com.parkeasy.android.data.places

import com.parkeasy.android.data.places.remote.PlacesApiService
import com.parkeasy.android.di.UniversalTutorialApiService
import retrofit2.await
import javax.inject.Inject

class PlacesRepository @Inject constructor(@UniversalTutorialApiService private val placesApiService: PlacesApiService) {
    private suspend fun getAuthToken(): String {
        val authTokenResponse = placesApiService.getAuthToken().await()
        return "Bearer ${authTokenResponse.authToken}"
    }

    suspend fun getAllCountries(): List<String> {
        val authToken = getAuthToken()
        val countriesResponse = placesApiService.getAllCountries(authToken).await()

        val countries = countriesResponse.map { countryResponse ->
            countryResponse.name
        }

        return countries
    }

    suspend fun getAllStatesByCountry(countryName: String): List<String> {
        val authToken = getAuthToken()
        val statesResponse = placesApiService.getAllStatesByCountry(countryName, authToken).await()

        return statesResponse.map { it.name }
    }

    suspend fun getAllCitiesByState(stateName: String): List<String> {
        val authToken = getAuthToken()
        val citiesResponse = placesApiService.getAllCitiesByState(stateName, authToken).await()

        return citiesResponse.map { it.name }
    }
}