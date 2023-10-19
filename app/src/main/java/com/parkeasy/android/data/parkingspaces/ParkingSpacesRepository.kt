package com.parkeasy.android.data.parkingspaces

import com.parkeasy.android.data.parkingspaces.model.toDomain
import com.parkeasy.android.data.parkingspaces.remote.ParkingSpacesRemoteDataSource
import com.parkeasy.android.domain.model.parkingspaces.ParkingSpace
import com.parkeasy.android.domain.model.parkingspaces.toModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ParkingSpacesRepository @Inject constructor(private val remoteDataSource: ParkingSpacesRemoteDataSource) {
    suspend fun add(parkingSpace: ParkingSpace) {
        val parkingSpaceModel = parkingSpace.toModel()
        remoteDataSource.add(parkingSpaceModel)
    }

    fun getAll() = remoteDataSource.getAll().map { it.map { model -> model.toDomain() } }

    fun getByParkingLot(parkingLotId: String) =
        remoteDataSource.getByParkingLot(parkingLotId).map { it.map { model -> model.toDomain() } }

    fun getById(id: String) = remoteDataSource.getById(id).map { it!!.toDomain() }
}