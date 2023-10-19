package com.parkeasy.android.domain.usecase.reservations

import com.parkeasy.android.data.reservations.ReservationsRepository
import javax.inject.Inject

class DeleteReservationUseCase @Inject constructor(private val reservationsRepository: ReservationsRepository) {
    suspend operator fun invoke(reservationId: String) = reservationsRepository.delete(reservationId)
}