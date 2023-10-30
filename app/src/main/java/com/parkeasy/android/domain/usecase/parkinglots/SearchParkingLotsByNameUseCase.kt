package com.parkeasy.android.domain.usecase.parkinglots

import com.parkeasy.android.data.parkinglots.ParkingLotsRepository
import javax.inject.Inject

/**
 * SearchParkingLotsByNameUseCase class.
 */
class SearchParkingLotsByNameUseCase @Inject constructor(private val parkingLotsRepository: ParkingLotsRepository) {
    /**
     * Invokes the `invoke` operator function of the `SearchParkingLotsByNameUseCase` class.
     *
     * @param name the name of the parking lot to search for
     * @return a list of parking lots matching the given name
     */
    operator fun invoke(name: String) = parkingLotsRepository.searchByName(name)
}