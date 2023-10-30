package com.parkeasy.android.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkeasy.android.domain.usecase.auth.GetCurrentUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * NavigationViewModel class.
 */
@HiltViewModel
class NavigationViewModel @Inject constructor(private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase) :
    ViewModel() {
    var uiState by mutableStateOf(NavigationUiState())
        private set

    init {
        getCurrentUserId()
    }

    /**
     * Retrieves the current user ID asynchronously and updates the UI state accordingly.
     *
     * This method launches a coroutine in the [viewModelScope] to execute the [getCurrentUserIdUseCase].
     * The retrieved user ID is then used to update the [uiState] by setting the [isLoggedIn] flag to `true`
     * if the user ID is not empty, and the [isLoading] flag to `false`.
     */
    private fun getCurrentUserId() {
        viewModelScope.launch {
            val currentUserId = getCurrentUserIdUseCase()
            uiState = uiState.copy(isLoggedIn = currentUserId.isNotEmpty(), isLoading = false)
        }
    }
}

/**
 * NavigationUiState class.
 */
data class NavigationUiState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = true
)