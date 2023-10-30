package com.parkeasy.android.domain.model.parkingspaces

import com.parkeasy.android.data.parkingspaces.model.ParkingSpaceModel

/**
 * ParkingSpace class.
 */
data class ParkingSpace(
    val id: String = "",
    val parkingLotId: String = "",
    val number: Int = 0
)

/**
 * Converts a ParkingSpace object to its corresponding ParkingSpaceModel
 * representation.
 *
 * Parameters:
 * - None
 *
 * Returns:
 * - A ParkingSpaceModel object representing the converted ParkingSpace.
 *
 * Example usage:
 * ```
 * val parkingSpace = ParkingSpace(id = 1, parkingLotId = 1, number = 5)
 * val parkingSpaceModel = parkingSpace.toModel()
 * ```
 *
 * Note: This method assumes that the ParkingSpace object has valid values for its
 * id, parkingLotId, and number properties.
 */
fun ParkingSpace.toModel() =
    ParkingSpaceModel(id = id, parkingLotId = parkingLotId, number = number)