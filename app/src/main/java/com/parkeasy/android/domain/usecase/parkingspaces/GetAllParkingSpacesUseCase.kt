package com.parkeasy.android.domain.usecase.parkingspaces

import com.parkeasy.android.data.parkingspaces.ParkingSpacesRepository
import javax.inject.Inject

class GetAllParkingSpacesUseCase @Inject constructor(private val parkingSpacesRepository: ParkingSpacesRepository) {
    operator fun invoke() = parkingSpacesRepository.getAll()
}