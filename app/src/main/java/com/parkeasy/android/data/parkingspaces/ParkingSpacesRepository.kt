package com.parkeasy.android.data.parkingspaces

import com.parkeasy.android.data.parkingspaces.model.toDomain
import com.parkeasy.android.data.parkingspaces.remote.ParkingSpacesRemoteDataSource
import com.parkeasy.android.domain.model.parkingspaces.ParkingSpace
import com.parkeasy.android.domain.model.parkingspaces.toModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * ParkingSpacesRepository class.
 */
class ParkingSpacesRepository @Inject constructor(private val remoteDataSource: ParkingSpacesRemoteDataSource) {
    /**
     * Adds a parking space to the remote data source.
     *
     * @param parkingSpace the parking space to be added.
     */
    suspend fun add(parkingSpace: ParkingSpace) {
        val parkingSpaceModel = parkingSpace.toModel()
        remoteDataSource.add(parkingSpaceModel)
    }

    /**
     * Retrieves all parking spaces from the remote data source and maps them to domain models.
     *
     * @return A list of parking spaces in the domain model format.
     */
    fun getAll() = remoteDataSource.getAll().map { it.map { model -> model.toDomain() } }

    /**
     * Retrieves a list of parking spaces by the given parking lot ID.
     *
     * @param parkingLotId The ID of the parking lot.
     * @return A [List] of parking spaces associated with the specified parking lot ID.
     */
    fun getByParkingLot(parkingLotId: String) =
        remoteDataSource.getByParkingLot(parkingLotId).map { it.map { model -> model.toDomain() } }

    /**
     * Retrieves a parking space by its ID from the remote data source.
     *
     * @param id The ID of the parking space to retrieve.
     * @return A [Flow] emitting the parking space with the specified ID, mapped to the domain model.
     * @throws [NullPointerException] if the parking space with the specified ID is null.
     */
    fun getById(id: String) = remoteDataSource.getById(id).map { it!!.toDomain() }
}