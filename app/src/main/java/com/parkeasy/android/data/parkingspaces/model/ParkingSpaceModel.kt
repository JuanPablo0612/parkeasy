package com.parkeasy.android.data.parkingspaces.model

import com.parkeasy.android.domain.model.parkingspaces.ParkingSpace

data class ParkingSpaceModel(
    val id: String,
    val parkingLotId: String,
    val number: Int
) {
    constructor() : this(id = "", parkingLotId = "", number = 0)
}

fun ParkingSpaceModel.toDomain() = ParkingSpace(id = id, parkingLotId = parkingLotId, number = number)