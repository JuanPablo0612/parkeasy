package com.parkeasy.android.data.auth

import com.parkeasy.android.data.auth.local.AuthLocalDataSource
import com.parkeasy.android.data.auth.remote.AuthRemoteDataSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * AuthRepository class.
 */
class AuthRepository @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource
) {
    /**
     * Retrieves the current user ID from the local data source.
     *
     * @return A [String] containing the current user ID.
     */
    suspend fun getCurrentUserId() = localDataSource.getCurrentUserId().first()

    /**
     * Logs in the user with the provided email and password.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     */
    suspend fun login(email: String, password: String) {
        val userId = remoteDataSource.login(email, password)
        localDataSource.saveUserId(userId)
    }

    /**
     * Registers a new user with the provided email and password.
     *
     * @param email The email of the user to be registered.
     * @param password The password of the user to be registered.
     * @return The unique identifier of the registered user.
     */
    suspend fun register(email: String, password: String): String {
        val userId = remoteDataSource.register(email, password)
        localDataSource.saveUserId(userId)
        return userId
    }

    /**
     * Logs out the user by calling the [logout] method in the [remoteDataSource] and deletes the user ID from the [localDataSource].
     */
    suspend fun logout() {
        remoteDataSource.logout()
        localDataSource.deleteUserId()
    }
}