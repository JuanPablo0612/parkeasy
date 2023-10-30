package com.parkeasy.android.data.parkingspaces.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.parkeasy.android.data.parkingspaces.model.ParkingSpaceModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * ParkingSpacesRemoteDataSource class.
 */
class ParkingSpacesRemoteDataSource @Inject constructor(private val firestore: FirebaseFirestore) {
    /**
     * Adds a new parking space to the Firestore database.
     *
     * @param parkingSpaceModel the model representing the parking space to be added
     */
    suspend fun add(parkingSpaceModel: ParkingSpaceModel) {
        // Adds the parking space model to the "parkingSpaces" collection in Firestore
        val document = firestore.collection("parkingSpaces").add(parkingSpaceModel).await()

        // Updates the "id" field of the parking space document with the generated document ID
        document.update("id", document.id).await()
    }

    /**
     * Retrieves all parking spaces from the Firestore database.
     *
     * @return A list of [ParkingSpaceModel] objects representing the parking spaces.
     */
    fun getAll() = firestore.collection("parkingSpaces").dataObjects<ParkingSpaceModel>()

    /**
     * Retrieves a list of parking spaces by the given parking lot ID.
     *
     * @param parkingLotId The ID of the parking lot to filter the parking spaces by.
     * @return A list of parking spaces that belong to the specified parking lot.
     */
    fun getByParkingLot(parkingLotId: String) =
        firestore.collection("parkingSpaces").whereEqualTo("parkingLotId", parkingLotId)
            .dataObjects<ParkingSpaceModel>()

    /**
     * Retrieves a parking space by its ID from the Firestore database.
     *
     * @param id The ID of the parking space to retrieve.
     * @return A [Flow] of [ParkingSpaceModel] objects representing the retrieved parking space.
     */
    fun getById(id: String) =
        firestore.collection("parkingSpaces").document(id).dataObjects<ParkingSpaceModel>()
}