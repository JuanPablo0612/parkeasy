package com.parkeasy.android.domain.model.reservations

import com.google.firebase.Timestamp
import com.parkeasy.android.data.reservations.model.ReservationModel

data class Reservation(
    val id: String = "",
    val userId: String = "",
    val parkingLotId: String = "",
    val parkingSpaceId: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val startTimestamp: Timestamp = Timestamp.now(),
    val endTimestamp: Timestamp = Timestamp.now(),
)

fun Reservation.toModel() = ReservationModel(
    id = id,
    userId = userId,
    parkingLotId = parkingLotId,
    parkingSpaceId = parkingSpaceId,
    startTimestamp = startTimestamp,
    endTimestamp = endTimestamp
)