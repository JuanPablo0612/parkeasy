package com.parkeasy.android.domain.model.reservations

import com.google.firebase.Timestamp
import com.parkeasy.android.data.reservations.model.ReservationModel

/**
 * Reservation class.
 */
data class Reservation(
    val id: String = "",
    val userId: String = "",
    val parkingLotId: String = "",
    val parkingSpaceId: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val startTimestamp: Timestamp = Timestamp.now(),
    val endTimestamp: Timestamp = Timestamp.now(),
)

/**
 * Converts a Reservation object to a ReservationModel object.
 *
 * Parameters:
 * - None
 *
 * Returns:
 * - A ReservationModel object with the following properties:
 * - id: The unique identifier of the reservation.
 * - userId: The unique identifier of the user who made the reservation.
 * - parkingLotId: The unique identifier of the parking lot where the reservation
 * is made.
 * - parkingSpaceId: The unique identifier of the parking space reserved.
 * - startTimestamp: The timestamp indicating the start time of the reservation.
 * - endTimestamp: The timestamp indicating the end time of the reservation.
 *
 * Throws:
 * - None
 */
fun Reservation.toModel() = ReservationModel(
    id = id,
    userId = userId,
    parkingLotId = parkingLotId,
    parkingSpaceId = parkingSpaceId,
    startTimestamp = startTimestamp,
    endTimestamp = endTimestamp
)