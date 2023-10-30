package com.parkeasy.android.domain.usecase.parkingspaces

import com.parkeasy.android.data.parkingspaces.ParkingSpacesRepository
import javax.inject.Inject

/**
 * GetParkingSpacesByParkingLotUseCase class.
 */
class GetParkingSpacesByParkingLotUseCase @Inject constructor(private val parkingSpacesRepository: ParkingSpacesRepository) {
    /**
     * Retrieves the parking spaces associated with the specified parking lot.
     *
     * @param parkingLotId the ID of the parking lot
     * @return a list of parking spaces associated with the parking lot
     */
    operator fun invoke(parkingLotId: String) =
        parkingSpacesRepository.getByParkingLot(parkingLotId)
}