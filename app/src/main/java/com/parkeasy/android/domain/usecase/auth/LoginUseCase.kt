package com.parkeasy.android.domain.usecase.auth

import com.parkeasy.android.data.auth.AuthRepository
import javax.inject.Inject

/**
 * LoginUseCase class.
 */
class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    /**
     * Suspends the execution of the current coroutine and invokes the login operation.
     *
     * @param email The email address of the user.
     * @param password The password of the user.
     * @return A [Result] object representing the result of the login operation.
     *         The [Result] object contains either a [LoggedInUser] if the login is successful,
     *         or a [LoginError] if the login fails.
     * @throws [Exception] if an error occurs during the login operation.
     */
    suspend operator fun invoke(email: String, password: String) =
        authRepository.login(email, password)
}