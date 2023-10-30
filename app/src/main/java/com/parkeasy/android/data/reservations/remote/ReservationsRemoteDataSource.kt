package com.parkeasy.android.data.reservations.remote

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.parkeasy.android.data.reservations.model.ReservationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * ReservationsRemoteDataSource class.
 */
class ReservationsRemoteDataSource @Inject constructor(private val firestore: FirebaseFirestore) {
    /**
     * Retrieves a list of reservations for a given user.
     *
     * @param userId The ID of the user.
     * @return A [Flow] emitting a list of [ReservationModel] objects.
     */
    fun getByUser(userId: String): Flow<List<ReservationModel>> =
        firestore.collection("reservations").whereEqualTo("userId", userId)
            .dataObjects<ReservationModel>()

    /**
     * Retrieves a list of reservations by parking lot ID.
     *
     * @param parkingLotId The ID of the parking lot.
     * @return A [Flow] emitting a list of [ReservationModel] objects.
     */
    fun getByParking(parkingLotId: String): Flow<List<ReservationModel>> =
        firestore.collection("reservations").whereEqualTo("parkingLotId", parkingLotId)
            .dataObjects<ReservationModel>()

    /**
     * Adds a reservation to the "reservations" collection in Firestore.
     *
     * @param reservationModel The reservation model to be added.
     * @throws Exception if an error occurs while adding the reservation.
     */
    suspend fun addReservation(reservationModel: ReservationModel) {
        // Adds the reservation model to the "reservations" collection in Firestore
        val document = firestore.collection("reservations").add(reservationModel).await()

        // Updates the document with the generated ID and server timestamp
        document.update(
            "id", document.id,
            "timestamp", FieldValue.serverTimestamp()
        ).await()
    }

    /**
     * Deletes a reservation from the Firestore database.
     *
     * @param reservationId the ID of the reservation to be deleted
     * @throws Exception if an error occurs while deleting the reservation
     */
    suspend fun deleteReservation(reservationId: String) {
        firestore.collection("reservations").document(reservationId).delete().await()
    }

    /**
     * Checks the availability of a parking space for a given time period.
     *
     * @param parkingSpaceId The ID of the parking space to check availability for.
     * @param startTimestamp The starting timestamp of the time period to check.
     * @param endTimestamp The ending timestamp of the time period to check.
     *
     * @return `true` if the parking space is available for the given time period, `false` otherwise.
     */
    suspend fun checkAvailability(
        parkingSpaceId: String,
        startTimestamp: Timestamp,
        endTimestamp: Timestamp
    ): Boolean {
        val reservations = getByParking(parkingSpaceId)
        val reserved = reservations.firstOrNull()?.any { reservation ->
            val start1 = reservation.startTimestamp!!.toDate()
            val end1 = reservation.endTimestamp!!.toDate()
            val start2 = startTimestamp.toDate()
            val end2 = endTimestamp.toDate()

            (start1.after(start2) && start1.before(end2)) || (end1.after(start2) && end1.before(end2))
        } ?: false

        return !reserved
    }
}