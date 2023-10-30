package com.parkeasy.android.data.reservations.model

import com.google.firebase.Timestamp
import com.parkeasy.android.domain.model.reservations.Reservation

/**
 * ReservationModel class.
 */
data class ReservationModel(
    val id: String,
    val userId: String,
    val parkingLotId: String,
    val parkingSpaceId: String,
    val timestamp: Timestamp? = Timestamp.now(),
    val startTimestamp: Timestamp? = Timestamp.now(),
    val endTimestamp: Timestamp? = Timestamp.now(),
) {
    /**
     * Constructs a new instance of the [ReservationModel] class with the specified parameters.
     *
     * @param id the unique identifier of the reservation
     * @param userId the unique identifier of the user associated with the reservation
     * @param parkingLotId the unique identifier of the parking lot associated with the reservation
     * @param parkingSpaceId the unique identifier of the parking space associated with the reservation
     * @param timestamp the timestamp when the reservation was created
     * @param startTimestamp the timestamp when the reservation starts
     * @param endTimestamp the timestamp when the reservation ends
     */
    constructor() : this(
        id = "",
        userId = "",
        parkingLotId = "",
        parkingSpaceId = "",
        timestamp = Timestamp.now(),
        startTimestamp = Timestamp.now(),
        endTimestamp = Timestamp.now()
    )
}

/**
 * Converts a ReservationModel object to a Reservation object in the domain layer.
 *
 * Parameters:
 * - id: The unique identifier of the reservation.
 * - userId: The unique identifier of the user who made the reservation.
 * - parkingLotId: The unique identifier of the parking lot where the reservation
 * is made.
 * - parkingSpaceId: The unique identifier of the parking space reserved.
 * - timestamp: The timestamp when the reservation was created. If not provided,
 * the current timestamp will be used.
 * - startTimestamp: The timestamp when the reservation starts. If not provided,
 * the current timestamp will be used.
 * - endTimestamp: The timestamp when the reservation ends. If not provided, the
 * current timestamp will be used.
 *
 * Returns:
 * A Reservation object in the domain layer with the provided data.
 */
fun ReservationModel.toDomain() = Reservation(
    id = id,
    userId = userId,
    parkingLotId = parkingLotId,
    parkingSpaceId = parkingSpaceId,
    timestamp = timestamp ?: Timestamp.now(),
    startTimestamp = startTimestamp ?: Timestamp.now(),
    endTimestamp = endTimestamp ?: Timestamp.now()
)