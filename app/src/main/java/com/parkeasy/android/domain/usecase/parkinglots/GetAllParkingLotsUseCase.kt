package com.parkeasy.android.domain.usecase.parkinglots

import com.parkeasy.android.data.parkinglots.ParkingLotsRepository
import javax.inject.Inject

/**
 * GetAllParkingLotsUseCase class.
 */
class GetAllParkingLotsUseCase @Inject constructor(private val parkingLotsRepository: ParkingLotsRepository) {
    /**
     * Invokes the [GetAllParkingLotsUseCase] to retrieve all parking lots.
     *
     * @return a list of all parking lots.
     */
    operator fun invoke() = parkingLotsRepository.getAll()
}