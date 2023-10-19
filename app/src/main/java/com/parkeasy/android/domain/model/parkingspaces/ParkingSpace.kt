package com.parkeasy.android.domain.model.parkingspaces

import com.parkeasy.android.data.parkingspaces.model.ParkingSpaceModel

data class ParkingSpace(
    val id: String = "",
    val parkingLotId: String = "",
    val number: Int = 0
)

fun ParkingSpace.toModel() = ParkingSpaceModel(id = id, parkingLotId = parkingLotId, number = number)