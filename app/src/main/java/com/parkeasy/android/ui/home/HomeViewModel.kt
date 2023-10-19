package com.parkeasy.android.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkeasy.android.domain.model.reservations.Reservation
import com.parkeasy.android.domain.model.users.User
import com.parkeasy.android.domain.usecase.reservations.GetReservationsByUserUseCase
import com.parkeasy.android.domain.usecase.users.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getReservationsByUserUseCase: GetReservationsByUserUseCase
) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        getUserReservations()
    }

    private fun getUserReservations() {
        viewModelScope.launch {
            getCurrentUserUseCase().collect { user ->
                getReservationsByUserUseCase(user.id).collect { reservations ->
                    uiState = uiState.copy(currentUser = user, reservations = reservations)
                }
            }
        }
    }
}

data class HomeUiState(
    val currentUser: User = User(),
    val reservations: List<Reservation> = emptyList(),
)