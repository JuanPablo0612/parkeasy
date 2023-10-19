package com.parkeasy.android.domain.usecase.auth

import com.parkeasy.android.data.auth.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke() = authRepository.logout()
}