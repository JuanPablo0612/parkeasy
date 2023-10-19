package com.parkeasy.android.domain.usecase.places

import com.parkeasy.android.data.places.PlacesRepository
import javax.inject.Inject

class GetAllCitiesByStateUseCase @Inject constructor(private val placesRepository: PlacesRepository) {
    suspend operator fun invoke(stateName: String) = placesRepository.getAllCitiesByState(stateName)
}