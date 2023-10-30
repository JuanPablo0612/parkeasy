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

/**
 * RegisterViewModel class.
 */
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

    /**
     * Asynchronously retrieves all countries and updates the UI state.
     *
     * This method launches a coroutine in the [viewModelScope] to perform the operation.
     * Upon successful retrieval of countries, the UI state's residenceCountries property is updated with the retrieved countries.
     * If an exception occurs during the operation, the UI state's errorMessageId property is updated with the corresponding error message ID.
     */
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

    /**
     * Updates the UI state with the new email text.
     *
     * @param text The new email text entered by the user.
     */
    fun onEmailTextChanged(text: String) {
        uiState = uiState.copy(email = text)
        validateEmail()
    }

    /**
     * Validates the email address entered by the user.
     *
     * This method uses the [Patterns.EMAIL_ADDRESS] pattern to validate the email address.
     *
     * @return true if the email address is valid, false otherwise.
     */
    private fun validateEmail() {
        val pattern = Patterns.EMAIL_ADDRESS
        val isValidEmail = pattern.matcher(uiState.email).matches()
        uiState = uiState.copy(isValidEmail = isValidEmail)
    }

    /**
     * Updates the UI state with the new password text.
     *
     * @param text The new password text entered by the user.
     */
    fun onPasswordTextChanged(text: String) {
        uiState = uiState.copy(password = text)
        validatePassword()
        validateRepeatPassword()
    }

    /**
     * Validates the password entered by the user.
     *
     * The password is considered valid if its length is between 8 and 16 characters (inclusive).
     *
     * This method updates the [uiState] property of the [RegisterViewModel] class by setting the [isValidPassword] flag.
     */
    private fun validatePassword() {
        val isValidPassword = uiState.password.length in 8..16
        uiState = uiState.copy(isValidPassword = isValidPassword)
    }

    /**
     * Toggles the visibility of the password field in the UI.
     *
     * This method is called when the password visibility is changed by the user.
     * It updates the [uiState] by toggling the [showPassword] flag.
     * If the [showPassword] flag is true, the password field will be displayed as plain text.
     * If the [showPassword] flag is false, the password field will be displayed as masked text.
     */
    fun onPasswordVisibilityChanged() {
        uiState = uiState.copy(showPassword = !uiState.showPassword)
    }

    /**
     * Updates the UI state with the new value of the repeat password text.
     *
     * @param text The new value of the repeat password text.
     */
    fun onRepeatPasswordTextChanged(text: String) {
        uiState = uiState.copy(repeatPassword = text)
        validateRepeatPassword()
    }

    /**
     * Validates the repeated password entered by the user.
     *
     * This method compares the value of [uiState.repeatPassword] with [uiState.password] to determine if they are equal.
     * The result of the comparison is stored in [uiState.isValidRepeatPassword].
     *
     * @see uiState
     * @see uiState.repeatPassword
     * @see uiState.password
     * @see uiState.isValidRepeatPassword
     */
    private fun validateRepeatPassword() {
        val isValidRepeatPassword = uiState.repeatPassword == uiState.password
        uiState = uiState.copy(isValidRepeatPassword = isValidRepeatPassword)
    }

    /**
     * Updates the UI state with the new value of the first name text.
     *
     * @param text The new value of the first name text.
     */
    fun onFirstNameTextChanged(text: String) {
        uiState = uiState.copy(firstName = text)
        validateFirstName()
    }

    /**
     * Validates the first name entered by the user.
     *
     * This method checks if the first name entered by the user is not empty.
     *
     * @return true if the first name is valid, false otherwise.
     */
    private fun validateFirstName() {
        val isValidFirstName = uiState.firstName.isNotEmpty()
        uiState = uiState.copy(isValidFirstName = isValidFirstName)
    }

    /**
     * Updates the UI state with the provided last name text.
     *
     * @param text The last name text to be set in the UI state.
     */
    fun onLastNameTextChanged(text: String) {
        uiState = uiState.copy(lastName = text)
        validateLastName()
    }

    /**
     * Validates the last name entered by the user.
     *
     * This method checks if the last name entered by the user is not empty.
     *
     * @return true if the last name is valid, false otherwise.
     */
    private fun validateLastName() {
        val isValidLastName = uiState.lastName.isNotEmpty()
        uiState = uiState.copy(isValidLastName = isValidLastName)
    }

    /**
     * Updates the selected residence country in the UI state and triggers the
     * [onShowResidenceCountriesChanged] method.
     *
     * @param country The new selected residence country.
     */
    fun onSelectedResidenceCountryChanged(country: String) {
        uiState = uiState.copy(selectedResidenceCountry = country)
        onShowResidenceCountriesChanged()
    }

    /**
     * Toggles the visibility of the residence countries in the UI.
     *
     * This method updates the [uiState] by toggling the value of [showResidenceCountries].
     * If [showResidenceCountries] is currently true, it will be set to false, and vice versa.
     */
    fun onShowResidenceCountriesChanged() {
        uiState = uiState.copy(showResidenceCountries = !uiState.showResidenceCountries)
    }

    /**
     * Resets the register result in the UI state of the RegisterViewModel.
     * Sets the [isSuccess] and [errorMessageId] properties to null.
     */
    fun resetRegisterResult() {
        uiState = uiState.copy(isSuccess = null, errorMessageId = null)
    }

    /**
     * Performs user registration using the provided user input.
     *
     * This method is responsible for initiating the registration process by calling the [registerUseCase]
     * with the user's email, password, first name, last name, and residence country. It updates the [uiState]
     * to reflect the loading state, success state, or error state of the registration process.
     *
     * @throws Exception if an error occurs during the registration process.
     */
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

/**
 * RegisterUiState class.
 */
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