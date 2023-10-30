package com.parkeasy.android.data.auth.remote

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * AuthRemoteDataSource class.
 */
class AuthRemoteDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    @ApplicationContext private val context: Context
) {
    /**
     * Registers a new user with the provided email and password.
     *
     * @param email The email address of the user.
     * @param password The password for the user.
     * @return The unique identifier (UID) of the registered user, or an empty string if registration fails.
     */
    suspend fun register(email: String, password: String): String {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        return authResult.user?.uid ?: ""
    }

    /**
     * Asynchronously logs in a user with the provided email and password.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return The unique identifier (UID) of the logged-in user, or an empty string if login fails.
     */
    suspend fun login(email: String, password: String): String {
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        return authResult.user?.uid ?: ""
    }

    /**
     * Logs out the user by signing out from the authentication provider.
     */
    fun logout() {
        auth.signOut()
    }
}