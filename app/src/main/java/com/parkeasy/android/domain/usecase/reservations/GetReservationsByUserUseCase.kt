package com.parkeasy.android.domain.usecase.reservations

import com.parkeasy.android.data.reservations.ReservationsRepository
import javax.inject.Inject

class GetReservationsByUserUseCase @Inject constructor(private val reservationsRepository: ReservationsRepository) {
    operator fun invoke(userId: String) = reservationsRepository.getByUser(userId)
}