package com.parkeasy.android.domain.usecase.parkingspaces

import com.parkeasy.android.data.parkingspaces.ParkingSpacesRepository
import com.parkeasy.android.domain.model.parkingspaces.ParkingSpace
import javax.inject.Inject

class AddParkingSpaceUseCase @Inject constructor(private val parkingSpacesRepository: ParkingSpacesRepository) {
    suspend operator fun invoke(parkingLotId: String, number: Int) {
        val parkingSpace = ParkingSpace(parkingLotId = parkingLotId, number = number)
        parkingSpacesRepository.add(parkingSpace)
    }
}