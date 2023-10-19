package com.parkeasy.android.ui.register

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkeasy.android.data.exceptions.getMessageId
import com.parkeasy.android.domain.usecase.auth.RegisterUseCase
import com.parkeasy.android.domain.usecase.places.GetAllCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val registerUseCase: RegisterUseCase
) :
    ViewModel() {
    var uiState by mutableStateOf(RegisterUiState())
        private set

    init {
        getAllCountries()
    }

    private fun getAllCountries() {
        viewModelScope.launch {
            uiState = try {
                val countries = getAllCountriesUseCase()
                uiState.copy(residenceCountries = countries)
            } catch (e: Exception) {
                uiState.copy(errorMessageId = e.getMessageId())
            }
        }
    }

    fun onEmailTextChanged(text: String) {
        uiState = uiState.copy(email = text)
        validateEmail()
    }

    private fun validateEmail() {
        val pattern = Patterns.EMAIL_ADDRESS
        val isValidEmail = pattern.matcher(uiState.email).matches()
        uiState = uiState.copy(isValidEmail = isValidEmail)
    }

    fun onPasswordTextChanged(text: String) {
        uiState = uiState.copy(password = text)
        validatePassword()
        validateRepeatPassword()
    }

    private fun validatePassword() {
        val isValidPassword = uiState.password.length in 8..16
        uiState = uiState.copy(isValidPassword = isValidPassword)
    }

    fun onPasswordVisibilityChanged() {
        uiState = uiState.copy(showPassword = !uiState.showPassword)
    }

    fun onRepeatPasswordTextChanged(text: String) {
        uiState = uiState.copy(repeatPassword = text)
        validateRepeatPassword()
    }

    private fun validateRepeatPassword() {
        val isValidRepeatPassword = uiState.repeatPassword == uiState.password
        uiState = uiState.copy(isValidRepeatPassword = isValidRepeatPassword)
    }

    fun onFirstNameTextChanged(text: String) {
        uiState = uiState.copy(firstName = text)
        validateFirstName()
    }

    private fun validateFirstName() {
        val isValidFirstName = uiState.firstName.isNotEmpty()
        uiState = uiState.copy(isValidFirstName = isValidFirstName)
    }

    fun onLastNameTextChanged(text: String) {
        uiState = uiState.copy(lastName = text)
        validateLastName()
    }

    private fun validateLastName() {
        val isValidLastName = uiState.lastName.isNotEmpty()
        uiState = uiState.copy(isValidLastName = isValidLastName)
    }

    fun onSelectedResidenceCountryChanged(country: String) {
        uiState = uiState.copy(selectedResidenceCountry = country)
        onShowResidenceCountriesChanged()
    }

    fun onShowResidenceCountriesChanged() {
        uiState = uiState.copy(showResidenceCountries = !uiState.showResidenceCountries)
    }

    fun resetRegisterResult() {
        uiState = uiState.copy(isSuccess = null, errorMessageId = null)
    }

    fun onRegister() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            uiState = try {
                registerUseCase(
                    email = uiState.email.trim(),
                    password = uiState.password.trim(),
                    firstName = uiState.firstName.trim(),
                    lastName = uiState.lastName.trim(),
                    residenceCountry = uiState.selectedResidenceCountry!!
                )

                uiState.copy(isSuccess = true)
            } catch (e: Exception) {
                uiState.copy(isSuccess = false, errorMessageId = e.getMessageId())
            }

            uiState = uiState.copy(isLoading = false)
        }
    }
}

data class RegisterUiState(
    val email: String = "",
    val isValidEmail: Boolean = false,
    val password: String = "",
    val isValidPassword: Boolean = false,
    val showPassword: Boolean = false,
    val repeatPassword: String = "",
    val isValidRepeatPassword: Boolean = true,
    val firstName: String = "",
    val isValidFirstName: Boolean = false,
    val lastName: String = "",
    val isValidLastName: Boolean = false,
    val residenceCountries: List<String> = emptyList(),
    val selectedResidenceCountry: String? = null,
    val showResidenceCountries: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean? = null,
    val errorMessageId: Int? = null
)