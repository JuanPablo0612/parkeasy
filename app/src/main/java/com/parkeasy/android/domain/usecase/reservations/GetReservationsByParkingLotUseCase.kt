package com.parkeasy.android.domain.usecase.reservations

import com.parkeasy.android.data.reservations.ReservationsRepository
import javax.inject.Inject

class GetReservationsByParkingLotUseCase @Inject constructor(private val reservationsRepository: ReservationsRepository) {
    operator fun invoke(parkingLotId: String) = reservationsRepository.getByParkingLot(parkingLotId)
}