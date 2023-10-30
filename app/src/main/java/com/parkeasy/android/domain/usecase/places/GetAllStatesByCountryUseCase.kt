package com.parkeasy.android.domain.usecase.places

import com.parkeasy.android.data.places.PlacesRepository
import javax.inject.Inject

/**
 * GetAllStatesByCountryUseCase class.
 */
class GetAllStatesByCountryUseCase @Inject constructor(private val placesRepository: PlacesRepository) {
    /**
     * Retrieves a list of all states in a given country.
     *
     * @param countryName the name of the country to retrieve states from
     * @return a list of all states in the specified country
     */
    suspend operator fun invoke(countryName: String) =
        placesRepository.getAllStatesByCountry(countryName)
}