package com.parkeasy.android.domain.usecase.parkingspaces

import com.parkeasy.android.data.parkingspaces.ParkingSpacesRepository
import javax.inject.Inject

class GetParkingSpacesByParkingLotUseCase @Inject constructor(private val parkingSpacesRepository: ParkingSpacesRepository) {
    operator fun invoke(parkingLotId: String) =
        parkingSpacesRepository.getByParkingLot(parkingLotId)
}