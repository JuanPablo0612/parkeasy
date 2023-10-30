package com.parkeasy.android.domain.usecase.auth

import com.parkeasy.android.data.auth.AuthRepository
import javax.inject.Inject

/**
 * GetCurrentUserIdUseCase class.
 */
class GetCurrentUserIdUseCase @Inject constructor(private val authRepository: AuthRepository) {
    /**
     * Suspends the execution and returns the current user ID.
     *
     * @return The current user ID.
     */
    suspend operator fun invoke() = authRepository.getCurrentUserId()
}