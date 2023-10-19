package com.parkeasy.android.data.users.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.dataObjects
import com.parkeasy.android.data.users.model.UserModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(private val firestore: FirebaseFirestore) {
    suspend fun save(userModel: UserModel) {
        firestore.document("users/${userModel.id}").set(userModel, SetOptions.merge()).await()
    }

    fun get(id: String) = firestore.document("users/$id").dataObjects<UserModel>()
}