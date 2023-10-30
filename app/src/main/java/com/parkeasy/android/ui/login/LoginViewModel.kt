package com.parkeasy.android.ui.login

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkeasy.android.data.exceptions.getMessageId
import com.parkeasy.android.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * LoginViewModel class.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

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
     * Validates the email entered by the user.
     *
     * This method uses the [Patterns.EMAIL_ADDRESS] pattern to validate the email.
     * It matches the email entered by the user against the pattern and sets the [uiState.isValidEmail]
     * property to true if the email is valid, otherwise sets it to false.
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
    }

    /**
     * Validates the password entered by the user.
     *
     * The password is considered valid if its length is between 8 and 16 characters (inclusive).
     *
     * This method updates the [uiState] property of the [LoginViewModel] class by setting the [isValidPassword] flag.
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
     * Resets the login result in the [LoginViewModel] by setting the [isSuccess] and [errorMessageId] properties to null.
     * This method is used to clear the previous login result and prepare for a new login attempt.
     */
    fun resetLoginResult() {
        uiState = uiState.copy(isSuccess = null, errorMessageId = null)
    }

    /**
     * Performs the login operation.
     *
     * This method is responsible for handling the login process by calling the [loginUseCase] with the provided email and password.
     * It updates the [uiState] to reflect the loading state, success state, or error state of the login operation.
     *
     * @throws Exception if an error occurs during the login process.
     */
    fun onLogin() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            uiState = try {
                loginUseCase(email = uiState.email, password = uiState.password)
                uiState.copy(isSuccess = true)
            } catch (e: Exception) {
                uiState.copy(
                    isSuccess = false,
                    errorMessageId = e.getMessageId()
                )
            }

            uiState = uiState.copy(isLoading = false)
        }
    }
}

/**
 * LoginUiState class.
 */
data class LoginUiState(
    val email: String = "",
    val isValidEmail: Boolean = false,
    val password: String = "",
    val isValidPassword: Boolean = false,
    val showPassword: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean? = null,
    val errorMessageId: Int? = null
)