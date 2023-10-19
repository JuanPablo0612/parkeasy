package com.parkeasy.android.domain.usecase.parkinglots

import com.parkeasy.android.data.parkinglots.ParkingLotsRepository
import com.parkeasy.android.domain.model.parkinglots.ParkingLot
import javax.inject.Inject

class AddParkingLotUseCase @Inject constructor(private val parkingLotsRepository: ParkingLotsRepository) {
    suspend operator fun invoke(name: String) {
        val parkingLot = ParkingLot(name = name)
        parkingLotsRepository.add(parkingLot)
    }
}