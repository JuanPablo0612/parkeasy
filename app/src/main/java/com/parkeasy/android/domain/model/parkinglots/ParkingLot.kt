package com.parkeasy.android.domain.model.parkinglots

import com.parkeasy.android.data.parkinglots.model.ParkingLotModel

data class ParkingLot(
    val id: String = "",
    val name: String = "",
    val country: String = "",
    val city: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

fun ParkingLot.toModel() = ParkingLotModel(
    id = id,
    name = name,
    country = country,
    city = city,
    latitude = latitude,
    longitude = longitude
)