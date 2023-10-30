package com.parkeasy.android.data.parkinglots.model

import com.parkeasy.android.domain.model.parkinglots.ParkingLot

/**
 * ParkingLotModel class.
 */
data class ParkingLotModel(
    val id: String,
    val name: String,
    val country: String = "",
    val city: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) {
    /**
     * Constructs a new instance of the [ParkingLotModel] class with the specified parameters.
     *
     * @param id the ID of the parking lot
     * @param name the name of the parking lot
     * @param country the country where the parking lot is located
     * @param city the city where the parking lot is located
     * @param latitude the latitude coordinate of the parking lot's location
     * @param longitude the longitude coordinate of the parking lot's location
     */
    constructor() : this(
        id = "", name = "",
        country = "",
        city = "",
        latitude = 0.0,
        longitude = 0.0
    )
}

/**
 * Converts a ParkingLotModel object to a ParkingLot domain object.
 *
 * Parameters:
 * - None
 *
 * Returns:
 * - ParkingLot: The converted ParkingLot domain object.
 */
fun ParkingLotModel.toDomain() = ParkingLot(
    id = id,
    name = name,
    country = country,
    city = city,
    latitude = latitude,
    longitude = longitude
)
