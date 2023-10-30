package com.parkeasy.android.ui.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkeasy.android.domain.model.users.User
import com.parkeasy.android.domain.usecase.auth.LogoutUseCase
import com.parkeasy.android.domain.usecase.users.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * AccountViewModel class.
 */
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    var uiState by mutableStateOf(AccountUiState())
        private set

    init {
        getCurrentUser()
    }

    /**
     * Retrieves the current user asynchronously and updates the UI state with the obtained user.
     * This method is called within the AccountViewModel class.
     *
     * @return Unit
     */
    private fun getCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase().collect { user ->
                uiState = uiState.copy(currentUser = user)
            }
        }
    }

    /**
     * Toggles the visibility of the logout dialog in the UI.
     *
     * This method updates the [uiState] property of the [AccountViewModel] class by toggling the value of [showLogoutDialog].
     * If [showLogoutDialog] is currently true, it will be set to false, and vice versa.
     */
    fun onShowLogoutDialogChanged() {
        uiState = uiState.copy(showLogoutDialog = !uiState.showLogoutDialog)
    }

    /**
     * Executes the logout process asynchronously.
     *
     * This method launches a coroutine in the viewModelScope to execute the logoutUseCase, which handles the logout process.
     * After the logout process is completed, the onShowLogoutDialogChanged method is called to show the logout dialog.
     */
    fun onLogout() {
        viewModelScope.launch {
            logoutUseCase()
            onShowLogoutDialogChanged()
        }
    }
}

/**
 * AccountUiState class.
 */
data class AccountUiState(
    val currentUser: User = User(),
    val showLogoutDialog: Boolean = false
)