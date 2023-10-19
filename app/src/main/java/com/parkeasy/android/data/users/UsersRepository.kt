package com.parkeasy.android.data.users

import com.parkeasy.android.data.users.model.toDomain
import com.parkeasy.android.data.users.remote.UsersRemoteDataSource
import com.parkeasy.android.domain.model.users.User
import com.parkeasy.android.domain.model.users.toModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersRepository @Inject constructor(private val remoteDataSource: UsersRemoteDataSource) {
    suspend fun save(user: User) {
        val userModel = user.toModel()
        remoteDataSource.save(userModel)
    }

    fun get(id: String) = remoteDataSource.get(id).map { model -> model!!.toDomain() }
}