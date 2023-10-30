package com.parkeasy.android.domain.usecase.location

import com.parkeasy.android.data.location.LocationRepository
import javax.inject.Inject

/**
 * GetCurrentLocationUseCase class.
 */
class GetCurrentLocationUseCase @Inject constructor(private val locationRepository: LocationRepository) {
    /**
     * Invokes the [GetCurrentLocationUseCase] to retrieve the current location.
     *
     * @return the current location as a [Location] object.
     */
    operator fun invoke() = locationRepository.getCurrentLocation()
}