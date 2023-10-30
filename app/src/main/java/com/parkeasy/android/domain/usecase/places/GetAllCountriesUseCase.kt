package com.parkeasy.android.domain.usecase.places

import com.parkeasy.android.data.places.PlacesRepository
import javax.inject.Inject

/**
 * GetAllCountriesUseCase class.
 */
class GetAllCountriesUseCase @Inject constructor(private val placesRepository: PlacesRepository) {
    /**
     * Suspends the execution of the current coroutine and invokes the `invoke` operator function.
     *
     * @return a list of all countries obtained from the places repository.
     */
    suspend operator fun invoke() = placesRepository.getAllCountries()
}