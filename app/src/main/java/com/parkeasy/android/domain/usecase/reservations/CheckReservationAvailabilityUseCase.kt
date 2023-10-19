package com.parkeasy.android.domain.usecase.reservations

import com.google.firebase.Timestamp
import com.parkeasy.android.data.reservations.ReservationsRepository
import javax.inject.Inject

class CheckReservationAvailabilityUseCase @Inject constructor(private val reservationsRepository: ReservationsRepository) {
    suspend operator fun invoke(
        parkingSpaceId: String,
        startTimestamp: Timestamp,
        endTimestamp: Timestamp
    ) = reservationsRepository.checkAvailability(parkingSpaceId, startTimestamp, endTimestamp)
}