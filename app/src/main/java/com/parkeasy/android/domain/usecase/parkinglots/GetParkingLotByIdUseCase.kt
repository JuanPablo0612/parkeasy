package com.parkeasy.android.domain.usecase.parkinglots

import com.parkeasy.android.data.parkinglots.ParkingLotsRepository
import javax.inject.Inject

/**
 * GetParkingLotByIdUseCase class.
 */
class GetParkingLotByIdUseCase @Inject constructor(private val parkingLotsRepository: ParkingLotsRepository) {
    /**
     * Invokes the GetParkingLotByIdUseCase to retrieve a parking lot by its ID.
     *
     * @param id The ID of the parking lot to retrieve.
     * @return The parking lot with the specified ID.
     */
    operator fun invoke(id: String) = parkingLotsRepository.getById(id)
}