package com.parkeasy.android.domain.usecase.places

import com.parkeasy.android.data.places.PlacesRepository
import javax.inject.Inject

/**
 * GetAllCitiesByStateUseCase class.
 */
class GetAllCitiesByStateUseCase @Inject constructor(private val placesRepository: PlacesRepository) {
    /**
     * Retrieves all cities by state name.
     *
     * @param stateName the name of the state to retrieve cities for
     * @return a list of cities in the specified state
     */
    suspend operator fun invoke(stateName: String) = placesRepository.getAllCitiesByState(stateName)
}