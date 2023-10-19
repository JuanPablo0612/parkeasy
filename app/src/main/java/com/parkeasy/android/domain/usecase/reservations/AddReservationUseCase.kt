package com.parkeasy.android.domain.usecase.reservations

import com.google.firebase.Timestamp
import com.parkeasy.android.data.reservations.ReservationsRepository
import com.parkeasy.android.domain.model.reservations.Reservation
import javax.inject.Inject

class AddReservationUseCase @Inject constructor(private val reservationsRepository: ReservationsRepository) {
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