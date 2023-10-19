package com.parkeasy.android.domain.usecase.places

import com.parkeasy.android.data.places.PlacesRepository
import javax.inject.Inject

class GetAllCountriesUseCase @Inject constructor(private val placesRepository: PlacesRepository) {
    suspend operator fun invoke() = placesRepository.getAllCountries()
}