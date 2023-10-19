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

@HiltViewModel
class ParkingLotsMapViewModel @Inject constructor(private val getCurrentUserUseCase: GetCurrentUserUseCase) : ViewModel() {
    var uiState by mutableStateOf(ParkingsMapUiState())
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
}

data class ParkingsMapUiState(
    val currentUser: User = User()
)