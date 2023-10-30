package com.parkeasy.android.domain.usecase.parkingspaces

import com.parkeasy.android.data.parkingspaces.ParkingSpacesRepository
import com.parkeasy.android.domain.model.parkingspaces.ParkingSpace
import javax.inject.Inject

/**
 * AddParkingSpaceUseCase class.
 */
class AddParkingSpaceUseCase @Inject constructor(private val parkingSpacesRepository: ParkingSpacesRepository) {
    /**
     * Adds a parking space to the parking lot with the specified ID.
     *
     * @param parkingLotId the ID of the parking lot
     * @param number the number of the parking space
     */
    suspend operator fun invoke(parkingLotId: String, number: Int) {
        val parkingSpace = ParkingSpace(parkingLotId = parkingLotId, number = number)
        parkingSpacesRepository.add(parkingSpace)
    }
}