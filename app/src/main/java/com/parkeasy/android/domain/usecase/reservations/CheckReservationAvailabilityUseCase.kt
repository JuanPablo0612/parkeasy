package com.parkeasy.android.domain.usecase.reservations

import com.google.firebase.Timestamp
import com.parkeasy.android.data.reservations.ReservationsRepository
import javax.inject.Inject

/**
 * CheckReservationAvailabilityUseCase class.
 */
class CheckReservationAvailabilityUseCase @Inject constructor(private val reservationsRepository: ReservationsRepository) {
    /**
     * Suspends the execution of the current coroutine and invokes the `invoke` operator function.
     *
     * @param parkingSpaceId the ID of the parking space to check availability for
     * @param startTimestamp the start timestamp of the reservation
     * @param endTimestamp the end timestamp of the reservation
     * @return `true` if the parking space is available for the given reservation period, `false` otherwise
     */
    suspend operator fun invoke(
        parkingSpaceId: String,
        startTimestamp: Timestamp,
        endTimestamp: Timestamp
    ) = reservationsRepository.checkAvailability(parkingSpaceId, startTimestamp, endTimestamp)
}