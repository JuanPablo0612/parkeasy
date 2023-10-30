package com.parkeasy.android.domain.usecase.users

import com.parkeasy.android.data.users.UsersRepository
import com.parkeasy.android.domain.model.users.User
import com.parkeasy.android.domain.usecase.auth.GetCurrentUserIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * GetCurrentUserUseCase class.
 */
class GetCurrentUserUseCase @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val usersRepository: UsersRepository
) {
    /**
     * Suspends the execution and returns a [Flow] of [User] objects.
     *
     * This method retrieves the current user ID using the [GetCurrentUserIdUseCase] and then
     * calls the [get] method of the [usersRepository] to fetch the user details.
     *
     * @return a [Flow] of [User] objects representing the current user.
     */
    suspend operator fun invoke(): Flow<User> {
        val userId = getCurrentUserIdUseCase()
        return usersRepository.get(userId)
    }
}