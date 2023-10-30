package com.parkeasy.android.domain.usecase.reservations

import com.parkeasy.android.data.reservations.ReservationsRepository
import javax.inject.Inject

/**
 * DeleteReservationUseCase class.
 */
class DeleteReservationUseCase @Inject constructor(private val reservationsRepository: ReservationsRepository) {
    /**
     * Deletes a reservation with the specified reservation ID.
     *
     * @param reservationId the ID of the reservation to be deleted
     * @throws [ReservationsRepositoryException] if an error occurs while deleting the reservation
     */
    suspend operator fun invoke(reservationId: String) =
        reservationsRepository.delete(reservationId)
}