package com.parkeasy.android.domain.usecase.location

import com.parkeasy.android.data.location.LocationRepository
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(private val locationRepository: LocationRepository) {
    operator fun invoke() = locationRepository.getCurrentLocation()
}