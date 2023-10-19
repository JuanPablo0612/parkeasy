package com.parkeasy.android.domain.usecase.parkinglots

import com.parkeasy.android.data.parkinglots.ParkingLotsRepository
import javax.inject.Inject

class SearchParkingLotsByNameUseCase @Inject constructor(private val parkingLotsRepository: ParkingLotsRepository) {
    operator fun invoke(name: String) = parkingLotsRepository.searchByName(name)
}