package com.parkeasy.android.domain.usecase.parkinglots

import com.parkeasy.android.data.parkinglots.ParkingLotsRepository
import javax.inject.Inject

class GetParkingLotByIdUseCase @Inject constructor(private val parkingLotsRepository: ParkingLotsRepository) {
    operator fun invoke(id: String) = parkingLotsRepository.getById(id)
}