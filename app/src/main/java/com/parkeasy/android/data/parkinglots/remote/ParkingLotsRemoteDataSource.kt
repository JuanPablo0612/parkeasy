package com.parkeasy.android.data.parkinglots.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.parkeasy.android.data.parkinglots.model.ParkingLotModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * ParkingLotsRemoteDataSource class.
 */
class ParkingLotsRemoteDataSource @Inject constructor(private val firestore: FirebaseFirestore) {
    /**
     * Adds a new parking lot to the Firestore database.
     *
     * @param parkingLotModel the parking lot model to be added
     */
    suspend fun add(parkingLotModel: ParkingLotModel) {
        val document = firestore.collection("parkingLots").add(parkingLotModel).await()
        document.update("id", document.id).await()
    }

    /**
     * Retrieves all parking lots from the Firestore database.
     *
     * @return A list of [ParkingLotModel] objects representing the parking lots.
     */
    fun getAll() = firestore.collection("parkingLots").dataObjects<ParkingLotModel>()

    /**
     * Retrieves a parking lot by its ID from the Firestore database.
     *
     * @param id The ID of the parking lot to retrieve.
     * @return A [ParkingLotModel] object representing the retrieved parking lot.
     */
    fun getById(id: String) = firestore.document("parkingLots/$id").dataObjects<ParkingLotModel>()

    /**
     * Searches for parking lots by name.
     *
     * @param name the name to search for
     * @return a list of parking lots that match the given name
     */
    fun searchByName(name: String) = getAll().map { parkingLots ->
        parkingLots.filter { parking -> parking.name.contains(name, true) }
    }

    /**
     * Searches for parking lots by city.
     *
     * @param city The city to search for.
     * @return A list of parking lots filtered by the specified city.
     */
    fun searchByCity(city: String) = getAll().map { parkingLots ->
        parkingLots.filter { parking -> parking.city.contains(city, true) }
    }
}