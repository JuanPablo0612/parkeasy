package com.parkeasy.android.data.parkinglots.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.parkeasy.android.data.parkinglots.model.ParkingLotModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ParkingLotsRemoteDataSource @Inject constructor(private val firestore: FirebaseFirestore) {
    suspend fun add(parkingLotModel: ParkingLotModel) {
        val document = firestore.collection("parkingLots").add(parkingLotModel).await()
        document.update("id", document.id).await()
    }

    fun getAll() = firestore.collection("parkingLots").dataObjects<ParkingLotModel>()

    fun getById(id: String) = firestore.document("parkingLots/$id").dataObjects<ParkingLotModel>()

    fun searchByName(name: String) = getAll().map { parkingLots ->
        parkingLots.filter { parking -> parking.name.contains(name, true) }
    }

    fun searchByCity(city: String) = getAll().map { parkingLots ->
        parkingLots.filter { parking -> parking.city.contains(city, true) }
    }
}