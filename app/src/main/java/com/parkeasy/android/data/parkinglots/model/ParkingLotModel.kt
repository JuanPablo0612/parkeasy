package com.parkeasy.android.data.parkinglots.model

import com.parkeasy.android.domain.model.parkinglots.ParkingLot

data class ParkingLotModel(
    val id: String,
    val name: String,
    val country: String = "",
    val city: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) {
    constructor() : this(
        id = "", name = "",
        country = "",
        city = "",
        latitude = 0.0,
        longitude = 0.0
    )
}

fun ParkingLotModel.toDomain() = ParkingLot(
    id = id,
    name = name,
    country = country,
    city = city,
    latitude = latitude,
    longitude = longitude
)
