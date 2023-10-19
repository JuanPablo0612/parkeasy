package com.parkeasy.android.domain.usecase.auth

import com.parkeasy.android.data.auth.AuthRepository
import com.parkeasy.android.data.users.UsersRepository
import com.parkeasy.android.domain.model.users.User
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val usersRepository: UsersRepository
) {
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