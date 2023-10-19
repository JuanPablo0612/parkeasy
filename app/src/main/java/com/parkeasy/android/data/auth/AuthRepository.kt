package com.parkeasy.android.data.auth

import com.parkeasy.android.data.auth.local.AuthLocalDataSource
import com.parkeasy.android.data.auth.remote.AuthRemoteDataSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource
) {
    suspend fun getCurrentUserId() = localDataSource.getCurrentUserId().first()

    suspend fun login(email: String, password: String) {
        val userId = remoteDataSource.login(email, password)
        localDataSource.saveUserId(userId)
    }

    suspend fun register(email: String, password: String): String {
        val userId = remoteDataSource.register(email, password)
        localDataSource.saveUserId(userId)
        return userId
    }

    suspend fun logout() {
        remoteDataSource.logout()
        localDataSource.deleteUserId()
    }
}