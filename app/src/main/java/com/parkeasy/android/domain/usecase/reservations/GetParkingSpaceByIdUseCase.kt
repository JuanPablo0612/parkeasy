package com.parkeasy.android.domain.usecase.reservations

import com.parkeasy.android.data.parkingspaces.ParkingSpacesRepository
import javax.inject.Inject

class GetParkingSpaceByIdUseCase @Inject constructor(private val parkingSpacesRepository: ParkingSpacesRepository) {
    operator fun invoke(parkingSpaceId: String) = parkingSpacesRepository.getById(parkingSpaceId)
}