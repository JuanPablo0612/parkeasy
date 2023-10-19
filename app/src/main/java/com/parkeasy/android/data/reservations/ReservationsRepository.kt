package com.parkeasy.android.data.reservations

import com.google.firebase.Timestamp
import com.parkeasy.android.data.reservations.model.toDomain
import com.parkeasy.android.data.reservations.remote.ReservationsRemoteDataSource
import com.parkeasy.android.domain.model.reservations.Reservation
import com.parkeasy.android.domain.model.reservations.toModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReservationsRepository @Inject constructor(private val remoteDataSource: ReservationsRemoteDataSource) {
    fun getByUser(userId: String) = remoteDataSource.getByUser(userId)
        .map { it.map { model -> model.toDomain() } }

    fun getByParkingLot(parkingLotId: String) = remoteDataSource.getByParking(parkingLotId)
        .map { it.map { model -> model.toDomain() } }

    suspend fun add(reservation: Reservation) {
        val reservationModel = reservation.toModel()
        remoteDataSource.addReservation(reservationModel)
    }

    suspend fun delete(reservationId: String) = remoteDataSource.deleteReservation(reservationId)

    suspend fun checkAvailability(
        parkingSpaceId: String,
        startTimestamp: Timestamp,
        endTimestamp: Timestamp
    ) = remoteDataSource.checkAvailability(parkingSpaceId, startTimestamp, endTimestamp)
}