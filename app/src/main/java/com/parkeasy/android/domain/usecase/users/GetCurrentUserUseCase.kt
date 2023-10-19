package com.parkeasy.android.domain.usecase.users

import com.parkeasy.android.data.users.UsersRepository
import com.parkeasy.android.domain.model.users.User
import com.parkeasy.android.domain.usecase.auth.GetCurrentUserIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(): Flow<User> {
        val userId = getCurrentUserIdUseCase()
        return usersRepository.get(userId)
    }
}