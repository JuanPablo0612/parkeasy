package com.parkeasy.android.data.users

import com.parkeasy.android.data.users.model.toDomain
import com.parkeasy.android.data.users.remote.UsersRemoteDataSource
import com.parkeasy.android.domain.model.users.User
import com.parkeasy.android.domain.model.users.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * UsersRepository class.
 */
class UsersRepository @Inject constructor(private val remoteDataSource: UsersRemoteDataSource) {
    /**
     * Saves a user to the remote data source.
     *
     * @param user The user to be saved.
     * @throws [Exception] if an error occurs while saving the user.
     */
    suspend fun save(user: User) {
        val userModel = user.toModel()
        remoteDataSource.save(userModel)
    }

    /**
     * Retrieves a user with the specified ID from the remote data source.
     *
     * @param id The ID of the user to retrieve.
     * @return A [Flow] emitting the user with the specified ID, mapped to the domain model.
     * @throws [NullPointerException] if the user with the specified ID is not found in the remote data source.
     */
    fun get(id: String) = remoteDataSource.get(id).map { model -> model!!.toDomain() }
}