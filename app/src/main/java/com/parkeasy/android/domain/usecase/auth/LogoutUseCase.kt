package com.parkeasy.android.domain.usecase.auth

import com.parkeasy.android.data.auth.AuthRepository
import javax.inject.Inject

/**
 * LogoutUseCase class.
 */
class LogoutUseCase @Inject constructor(private val authRepository: AuthRepository) {
    /**
     * Suspends the execution of the current coroutine and invokes the `logout` method of the `authRepository`.
     *
     * @throws [Exception] if an error occurs during the logout process.
     */
    suspend operator fun invoke() = authRepository.logout()
}