package com.parkeasy.android.domain.usecase.auth

import com.parkeasy.android.data.auth.AuthRepository
import com.parkeasy.android.data.users.UsersRepository
import com.parkeasy.android.domain.model.users.User
import javax.inject.Inject

/**
 * RegisterUseCase class.
 */
class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val usersRepository: UsersRepository
) {
    /**
     * Registers a new user with the provided information.
     *
     * @param email The email address of the user.
     * @param password The password for the user's account.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param residenceCountry The country of residence for the user.
     *
     * @throws Exception if there is an error during the registration process.
     */
    suspend operator fun invoke(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        residenceCountry: String
    ) {
        val userId = authRepository.register(email, password)

        val user = User(
            id = userId,
            email = email,
            firstName = firstName,
            lastName = lastName,
            residenceCountry = residenceCountry
        )

        usersRepository.save(user)
    }
}