package com.parkeasy.android.domain.usecase.parkinglots

import com.parkeasy.android.data.parkinglots.ParkingLotsRepository
import javax.inject.Inject

class SearchParkingLotsByCityUseCase @Inject constructor(private val parkingLotsRepository: ParkingLotsRepository) {
    operator fun invoke(city: String) = parkingLotsRepository.searchByCity(city)
}