package com.parkeasy.android.data.places

import com.parkeasy.android.data.places.remote.PlacesApiService
import com.parkeasy.android.di.UniversalTutorialApiService
import retrofit2.await
import javax.inject.Inject

/**
 * PlacesRepository class.
 */
class PlacesRepository @Inject constructor(@UniversalTutorialApiService private val placesApiService: PlacesApiService) {
    /**
     * Suspended function that retrieves the authentication token from the Places API service.
     *
     * @return The authentication token as a string, formatted as "Bearer {authToken}".
     */
    private suspend fun getAuthToken(): String {
        val authTokenResponse = placesApiService.getAuthToken().await()
        return "Bearer ${authTokenResponse.authToken}"
    }

    /**
     * Retrieves a list of all countries.
     *
     * @return A list of country names.
     */
    suspend fun getAllCountries(): List<String> {
        val authToken = getAuthToken()
        val countriesResponse = placesApiService.getAllCountries(authToken).await()

        val countries = countriesResponse.map { countryResponse ->
            countryResponse.name
        }

        return countries
    }

    /**
     * Retrieves a list of all states in a given country.
     *
     * @param countryName the name of the country to retrieve states from
     * @return a list of state names in the specified country
     */
    suspend fun getAllStatesByCountry(countryName: String): List<String> {
        val authToken = getAuthToken()
        val statesResponse = placesApiService.getAllStatesByCountry(countryName, authToken).await()

        return statesResponse.map { it.name }
    }

    /**
     * Retrieves a list of all cities in a given state.
     *
     * @param stateName the name of the state to retrieve cities from
     * @return a list of city names in the given state
     */
    suspend fun getAllCitiesByState(stateName: String): List<String> {
        val authToken = getAuthToken()
        val citiesResponse = placesApiService.getAllCitiesByState(stateName, authToken).await()

        return citiesResponse.map { it.name }
    }
}