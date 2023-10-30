package com.parkeasy.android.data.parkingspaces.model

import com.parkeasy.android.domain.model.parkingspaces.ParkingSpace

/**
 * ParkingSpaceModel class.
 */
data class ParkingSpaceModel(
    val id: String,
    val parkingLotId: String,
    val number: Int
) {
    /**
     * Constructs a new instance of the ParkingSpaceModel class.
     *
     * @param id The ID of the parking space. Defaults to an empty string if not provided.
     * @param parkingLotId The ID of the parking lot that the space belongs to. Defaults to an empty string if not provided.
     * @param number The number of the parking space. Defaults to 0 if not provided.
     */
    constructor() : this(id = "", parkingLotId = "", number = 0)
}

/**
 * Converts a ParkingSpaceModel object to a corresponding ParkingSpace domain
 * object.
 *
 * Parameters:
 * - None
 *
 * Returns:
 * - ParkingSpace: The converted ParkingSpace domain object.
 */
fun ParkingSpaceModel.toDomain() =
    ParkingSpace(id = id, parkingLotId = parkingLotId, number = number)