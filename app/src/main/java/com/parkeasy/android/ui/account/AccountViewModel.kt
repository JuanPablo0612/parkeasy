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

    private fun getCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase().collect { user ->
                uiState = uiState.copy(currentUser = user)
            }
        }
    }

    fun onShowLogoutDialogChanged() {
        uiState = uiState.copy(showLogoutDialog = !uiState.showLogoutDialog)
    }

    fun onLogout() {
        viewModelScope.launch {
            logoutUseCase()
            onShowLogoutDialogChanged()
        }
    }
}

data class AccountUiState(
    val currentUser: User = User(),
    val showLogoutDialog: Boolean = false
)