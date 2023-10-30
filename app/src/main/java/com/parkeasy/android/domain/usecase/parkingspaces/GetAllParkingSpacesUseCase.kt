package com.parkeasy.android.domain.usecase.parkingspaces

import com.parkeasy.android.data.parkingspaces.ParkingSpacesRepository
import javax.inject.Inject

/**
 * GetAllParkingSpacesUseCase class.
 */
class GetAllParkingSpacesUseCase @Inject constructor(private val parkingSpacesRepository: ParkingSpacesRepository) {
    /**
     * Invokes the [GetAllParkingSpacesUseCase] to retrieve all parking spaces.
     *
     * @return a list of all parking spaces.
     */
    operator fun invoke() = parkingSpacesRepository.getAll()
}