package com.parkeasy.android.domain.usecase.parkinglots

import com.parkeasy.android.data.parkinglots.ParkingLotsRepository
import javax.inject.Inject

/**
 * SearchParkingLotsByCityUseCase class.
 */
class SearchParkingLotsByCityUseCase @Inject constructor(private val parkingLotsRepository: ParkingLotsRepository) {
    /**
     * Invokes the SearchParkingLotsByCityUseCase to search for parking lots by city.
     *
     * @param city The city to search for parking lots in.
     * @return A list of parking lots found in the specified city.
     */
    operator fun invoke(city: String) = parkingLotsRepository.searchByCity(city)
}