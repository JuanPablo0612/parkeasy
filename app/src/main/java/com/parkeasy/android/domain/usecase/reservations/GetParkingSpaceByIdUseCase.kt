package com.parkeasy.android.domain.usecase.reservations

import com.parkeasy.android.data.parkingspaces.ParkingSpacesRepository
import javax.inject.Inject

/**
 * GetParkingSpaceByIdUseCase class.
 */
class GetParkingSpaceByIdUseCase @Inject constructor(private val parkingSpacesRepository: ParkingSpacesRepository) {
    /**
     * Invokes the GetParkingSpaceByIdUseCase to retrieve a parking space by its ID.
     *
     * @param parkingSpaceId the ID of the parking space to retrieve
     * @return the parking space with the specified ID
     */
    operator fun invoke(parkingSpaceId: String) = parkingSpacesRepository.getById(parkingSpaceId)
}