package com.parkeasy.android.domain.usecase.reservations

import com.parkeasy.android.data.reservations.ReservationsRepository
import javax.inject.Inject

/**
 * GetReservationsByParkingLotUseCase class.
 */
class GetReservationsByParkingLotUseCase @Inject constructor(private val reservationsRepository: ReservationsRepository) {
    /**
     * Retrieves a list of reservations by parking lot ID.
     *
     * @param parkingLotId the ID of the parking lot to retrieve reservations for
     * @return a list of reservations associated with the specified parking lot
     */
    operator fun invoke(parkingLotId: String) = reservationsRepository.getByParkingLot(parkingLotId)
}