package com.parkeasy.android.domain.model.parkinglots

import com.parkeasy.android.data.parkinglots.model.ParkingLotModel

/**
 * ParkingLot class.
 */
data class ParkingLot(
    val id: String = "",
    val name: String = "",
    val country: String = "",
    val city: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

/**
 * Converts a ParkingLot object to its corresponding ParkingLotModel
 * representation.
 *
 * Parameters:
 * - None
 *
 * Returns:
 * - A ParkingLotModel object representing the converted ParkingLot.
 *
 * Example usage:
 * ```kotlin
 * val parkingLot = ParkingLot(
 * id = 1,
 * name = "ABC Parking",
 * country = "USA",
 * city = "New York",
 * latitude = 40.7128,
 * longitude = -74.0060
 * )
 *
 * val parkingLotModel = parkingLot.toModel()
 * ```
 *
 * Note: This method assumes that the ParkingLot object has valid values for all
 * its properties.
 */
fun ParkingLot.toModel() = ParkingLotModel(
    id = id,
    name = name,
    country = country,
    city = city,
    latitude = latitude,
    longitude = longitude
)