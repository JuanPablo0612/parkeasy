package com.parkeasy.android.data.parkinglots

import com.parkeasy.android.data.parkinglots.model.toDomain
import com.parkeasy.android.data.parkinglots.remote.ParkingLotsRemoteDataSource
import com.parkeasy.android.domain.model.parkinglots.ParkingLot
import com.parkeasy.android.domain.model.parkinglots.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * ParkingLotsRepository class.
 */
class ParkingLotsRepository @Inject constructor(private val remoteDataSource: ParkingLotsRemoteDataSource) {
    /**
     * Adds a parking lot to the remote data source.
     *
     * @param parkingLot the parking lot to be added
     */
    suspend fun add(parkingLot: ParkingLot) {
        val parkingLotModel = parkingLot.toModel()
        remoteDataSource.add(parkingLotModel)
    }

    /**
     * Retrieves all parking lots from the remote data source.
     *
     * @return A [Flow] emitting a list of [ParkingLot] objects.
     *
     * @see [ParkingLot]
     */
    fun getAll(): Flow<List<ParkingLot>> = remoteDataSource.getAll()
        .map { it.map { model -> model.toDomain() } }

    /**
     * Retrieves a parking lot by its ID.
     *
     * @param id The ID of the parking lot to retrieve.
     * @return A [Flow] emitting the [ParkingLot] object with the specified ID.
     */
    fun getById(id: String): Flow<ParkingLot> = remoteDataSource.getById(id)
        .map { it!!.toDomain() }

    /**
     * Searches for parking lots by name.
     *
     * @param name The name to search for.
     * @return A [Flow] emitting a list of [ParkingLot] objects matching the given name.
     */
    fun searchByName(name: String): Flow<List<ParkingLot>> =
        remoteDataSource.searchByName(name)
            .map { it.map { model -> model.toDomain() } }

    /**
     * Searches for parking lots by city.
     *
     * @param city The city to search for parking lots.
     * @return A [Flow] emitting a list of [ParkingLot] objects matching the specified city.
     */
    fun searchByCity(city: String): Flow<List<ParkingLot>> =
        remoteDataSource.searchByCity(city)
            .map { it.map { model -> model.toDomain() } }
}