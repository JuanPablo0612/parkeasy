package com.parkeasy.android.domain.usecase.reservations

import com.google.firebase.Timestamp
import com.parkeasy.android.data.reservations.ReservationsRepository
import com.parkeasy.android.domain.model.reservations.Reservation
import javax.inject.Inject

/**
 * AddReservationUseCase class.
 */
class AddReservationUseCase @Inject constructor(private val reservationsRepository: ReservationsRepository) {
    /**
     * Suspends the operation of adding a reservation for a parking space.
     *
     * @param userId The ID of the user making the reservation.
     * @param parkingId The ID of the parking lot where the reservation is being made.
     * @param parkingSpaceId The ID of the parking space being reserved.
     * @param startTimestamp The starting timestamp of the reservation.
     * @param endTimestamp The ending timestamp of the reservation.
     */
    suspend operator fun invoke(
        userId: String,
        parkingId: String,
        parkingSpaceId: String,
        startTimestamp: Timestamp,
        endTimestamp: Timestamp
    ) {
        val reservation = Reservation(
            userId = userId,
            parkingLotId = parkingId,
            parkingSpaceId = parkingSpaceId,
            startTimestamp = startTimestamp,
            endTimestamp = endTimestamp
        )

        reservationsRepository.add(reservation)
    }
}