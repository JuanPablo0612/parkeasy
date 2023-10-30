package com.parkeasy.android.data.reservations

import com.google.firebase.Timestamp
import com.parkeasy.android.data.reservations.model.toDomain
import com.parkeasy.android.data.reservations.remote.ReservationsRemoteDataSource
import com.parkeasy.android.domain.model.reservations.Reservation
import com.parkeasy.android.domain.model.reservations.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * ReservationsRepository class.
 */
class ReservationsRepository @Inject constructor(private val remoteDataSource: ReservationsRemoteDataSource) {
    /**
     * Retrieves a list of reservations by user ID.
     *
     * @param userId the ID of the user
     * @return a [List] of reservations associated with the user
     */
    fun getByUser(userId: String) = remoteDataSource.getByUser(userId)
        .map { it.map { model -> model.toDomain() } }

    /**
     * Retrieves a list of reservations by parking lot ID.
     *
     * @param parkingLotId the ID of the parking lot
     * @return a [Flow] emitting a list of reservations for the specified parking lot
     */
    fun getByParkingLot(parkingLotId: String) =
        remoteDataSource.getByParking(parkingLotId)
            .map { list ->
                list.map { model ->
                    model.toDomain()
                }
            }

    /**
     * Adds a new reservation to the remote data source.
     *
     * @param reservation the reservation to be added
     */
    suspend fun add(reservation: Reservation) {
        val reservationModel = reservation.toModel()
        remoteDataSource.addReservation(reservationModel)
    }

    /**
     * Deletes a reservation with the specified reservation ID.
     *
     * @param reservationId the ID of the reservation to be deleted
     * @return a [Unit] object representing the completion of the deletion process
     *
     * @throws [Exception] if an error occurs during the deletion process
     */
    suspend fun delete(reservationId: String) = remoteDataSource.deleteReservation(reservationId)

    /**
     * Checks the availability of a parking space for a given time period.
     *
     * @param parkingSpaceId The ID of the parking space to check availability for.
     * @param startTimestamp The start timestamp of the time period to check availability for.
     * @param endTimestamp The end timestamp of the time period to check availability for.
     *
     * @return `true` if the parking space is available for the given time period, `false` otherwise.
     */
    suspend fun checkAvailability(
        parkingSpaceId: String,
        startTimestamp: Timestamp,
        endTimestamp: Timestamp
    ) = remoteDataSource.checkAvailability(parkingSpaceId, startTimestamp, endTimestamp)
}