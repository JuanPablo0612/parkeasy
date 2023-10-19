package com.parkeasy.android.data.parkingspaces.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.parkeasy.android.data.parkingspaces.model.ParkingSpaceModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ParkingSpacesRemoteDataSource @Inject constructor(private val firestore: FirebaseFirestore) {
    suspend fun add(parkingSpaceModel: ParkingSpaceModel) {
        val document = firestore.collection("parkingSpaces").add(parkingSpaceModel).await()
        document.update("id", document.id).await()
    }

    fun getAll() = firestore.collection("parkingSpaces").dataObjects<ParkingSpaceModel>()

    fun getByParkingLot(parkingLotId: String) =
        firestore.collection("parkingSpaces").whereEqualTo("parkingLotId", parkingLotId)
            .dataObjects<ParkingSpaceModel>()

    fun getById(id: String) =
        firestore.collection("parkingSpaces").document(id).dataObjects<ParkingSpaceModel>()
}