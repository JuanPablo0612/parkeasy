package com.parkeasy.android.domain.usecase.reservations

import com.parkeasy.android.data.reservations.ReservationsRepository
import javax.inject.Inject

/**
 * GetReservationsByUserUseCase class.
 */
class GetReservationsByUserUseCase @Inject constructor(private val reservationsRepository: ReservationsRepository) {
    /**
     * Retrieves reservations for a specific user.
     *
     * @param userId the unique identifier of the user
     * @return a list of reservations associated with the user
     */
    operator fun invoke(userId: String) = reservationsRepository.getByUser(userId)
}