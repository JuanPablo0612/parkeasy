package com.parkeasy.android.data.parkinglots

import com.parkeasy.android.data.parkinglots.model.toDomain
import com.parkeasy.android.data.parkinglots.remote.ParkingLotsRemoteDataSource
import com.parkeasy.android.domain.model.parkinglots.ParkingLot
import com.parkeasy.android.domain.model.parkinglots.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ParkingLotsRepository @Inject constructor(private val remoteDataSource: ParkingLotsRemoteDataSource) {
    suspend fun add(parkingLot: ParkingLot) {
        val parkingLotModel = parkingLot.toModel()
        remoteDataSource.add(parkingLotModel)
    }

    fun getAll(): Flow<List<ParkingLot>> = remoteDataSource.getAll()
        .map { it.map { model -> model.toDomain() } }

    fun getById(id: String): Flow<ParkingLot> = remoteDataSource.getById(id)
        .map { it!!.toDomain() }

    fun searchByName(name: String): Flow<List<ParkingLot>> =
        remoteDataSource.searchByName(name)
            .map { it.map { model -> model.toDomain() } }

    fun searchByCity(city: String): Flow<List<ParkingLot>> =
        remoteDataSource.searchByCity(city)
            .map { it.map { model -> model.toDomain() } }
}