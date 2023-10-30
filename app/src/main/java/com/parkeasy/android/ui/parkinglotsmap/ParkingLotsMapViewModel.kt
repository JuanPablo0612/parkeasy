package com.parkeasy.android.ui.parkinglotsmap

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkeasy.android.domain.model.users.User
import com.parkeasy.android.domain.usecase.users.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ParkingLotsMapViewModel class.
 */
@HiltViewModel
class ParkingLotsMapViewModel @Inject constructor(private val getCurrentUserUseCase: GetCurrentUserUseCase) :
    ViewModel() {
    var uiState by mutableStateOf(ParkingsMapUiState())
        private set

    init {
        getCurrentUser()
    }

    /**
     * Retrieves the current user asynchronously and updates the UI state with the obtained user.
     * This method is called within the [viewModelScope] to ensure the coroutine is cancelled when the ViewModel is cleared.
     * The obtained user is collected using the [getCurrentUserUseCase] and stored in the [uiState] property of the ViewModel.
     *
     * @see viewModelScope
     * @see getCurrentUserUseCase
     * @see uiState
     */
    private fun getCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase().collect { user ->
                uiState = uiState.copy(currentUser = user)
            }
        }
    }
}

/**
 * ParkingsMapUiState class.
 */
data class ParkingsMapUiState(
    val currentUser: User = User()
)