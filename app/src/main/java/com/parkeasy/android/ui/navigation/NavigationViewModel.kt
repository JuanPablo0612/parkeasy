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

@HiltViewModel
class NavigationViewModel @Inject constructor(private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase) :
    ViewModel() {
    var uiState by mutableStateOf(NavigationUiState())
        private set

    init {
        getCurrentUserId()
    }

    private fun getCurrentUserId() {
        viewModelScope.launch {
            val currentUserId = getCurrentUserIdUseCase()
            uiState = uiState.copy(isLoggedIn = currentUserId.isNotEmpty(), isLoading = false)
        }
    }
}

data class NavigationUiState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = true
)