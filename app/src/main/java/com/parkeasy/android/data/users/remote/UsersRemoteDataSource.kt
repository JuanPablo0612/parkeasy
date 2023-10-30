package com.parkeasy.android.data.users.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.dataObjects
import com.parkeasy.android.data.users.model.UserModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * UsersRemoteDataSource class.
 */
class UsersRemoteDataSource @Inject constructor(private val firestore: FirebaseFirestore) {
    /**
     * Saves a user model to the Firestore database.
     *
     * @param userModel The user model to be saved.
     * @throws Exception if an error occurs while saving the user
     */
    suspend fun save(userModel: UserModel) {
        firestore.document("users/${userModel.id}").set(userModel, SetOptions.merge()).await()
    }

    /**
     * Retrieves a user with the specified ID from the Firestore database.
     *
     * @param id The ID of the user to retrieve.
     * @return A [UserModel] object representing the user with the specified ID.
     */
    fun get(id: String) = firestore.document("users/$id").dataObjects<UserModel>()
}