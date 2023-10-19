package com.parkeasy.android.data.reservations.remote

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.parkeasy.android.data.reservations.model.ReservationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReservationsRemoteDataSource @Inject constructor(private val firestore: FirebaseFirestore) {
    fun getByUser(userId: String): Flow<List<ReservationModel>> =
        firestore.collection("reservations").whereEqualTo("userId", userId)
            .dataObjects<ReservationModel>()

    fun getByParking(parkingLotId: String): Flow<List<ReservationModel>> =
        firestore.collection("reservations").whereEqualTo("parkingLotId", parkingLotId)
            .dataObjects<ReservationModel>()

    suspend fun addReservation(reservationModel: ReservationModel) {
        val document =
            firestore.collection("reservations").add(reservationModel).await()

        document.update(
            "id", document.id,
            "timestamp", FieldValue.serverTimestamp()
        ).await()
    }

    suspend fun deleteReservation(reservationId: String) {
        firestore.collection("reservations").document(reservationId).delete().await()
    }

    suspend fun checkAvailability(
        parkingSpaceId: String,
        startTimestamp: Timestamp,
        endTimestamp: Timestamp
    ): Boolean {
        val reservations = getByParking(parkingSpaceId).map {
            it.filter { reservation ->
                startTimestamp.toDate()
                    .before(reservation.endTimestamp!!.toDate()) && endTimestamp.toDate()
                    .after(reservation.startTimestamp!!.toDate())
            }
        }

        return reservations.first().isEmpty()
    }
}