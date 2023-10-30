package com.parkeasy.android.domain.usecase.parkinglots

import com.parkeasy.android.data.parkinglots.ParkingLotsRepository
import com.parkeasy.android.domain.model.parkinglots.ParkingLot
import javax.inject.Inject

/**
 * AddParkingLotUseCase class.
 */
class AddParkingLotUseCase @Inject constructor(private val parkingLotsRepository: ParkingLotsRepository) {
    /**
     * Suspends the execution of the current coroutine and adds a new parking lot to the repository.
     *
     * @param name the name of the parking lot to be added
     * @throws Exception if an error occurs while adding the parking lot
     */
    suspend operator fun invoke(name: String) {
        val parkingLot = ParkingLot(name = name)
        parkingLotsRepository.add(parkingLot)
    }
}