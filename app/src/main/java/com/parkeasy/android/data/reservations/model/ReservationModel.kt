package com.parkeasy.android.data.reservations.model

import com.google.firebase.Timestamp
import com.parkeasy.android.domain.model.reservations.Reservation

data class ReservationModel(
    val id: String,
    val userId: String,
    val parkingLotId: String,
    val parkingSpaceId: String,
    val timestamp: Timestamp? = Timestamp.now(),
    val startTimestamp: Timestamp? = Timestamp.now(),
    val endTimestamp: Timestamp? = Timestamp.now(),
) {
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

fun ReservationModel.toDomain() = Reservation(
    id = id,
    userId = userId,
    parkingLotId = parkingLotId,
    parkingSpaceId = parkingSpaceId,
    timestamp = timestamp?: Timestamp.now(),
    startTimestamp = startTimestamp?: Timestamp.now(),
    endTimestamp = endTimestamp?: Timestamp.now()
)