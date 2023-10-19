package com.parkeasy.android.ui.parkinglotsearch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkeasy.android.domain.model.parkinglots.ParkingLot
import com.parkeasy.android.domain.usecase.parkinglots.SearchParkingLotsByCityUseCase
import com.parkeasy.android.domain.usecase.parkinglots.SearchParkingLotsByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParkingLotSearchViewModel @Inject constructor(
    private val searchParkingLotsByNameUseCase: SearchParkingLotsByNameUseCase,
    private val searchParkingLotsByCityUseCase: SearchParkingLotsByCityUseCase
) : ViewModel() {
    var uiState by mutableStateOf(ParkingSearchUiState())
        private set

    fun onParkingNameTextChanged(text: String) {
        uiState = uiState.copy(parkingNameText = text)
    }

    fun validateParkingNameText() {
        uiState = uiState.copy(isValidParkingNameText = uiState.parkingNameText.isNotEmpty())
    }

    fun onCityTextChanged(text: String) {
        uiState = uiState.copy(cityText = text)
    }

    fun validateCityText() {
        uiState = uiState.copy(isValidCityText = uiState.cityText.isNotEmpty())
    }

    fun onSearch() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            searchParkingLotsByNameUseCase(uiState.parkingNameText).collect { parkingLots ->
                uiState = uiState.copy(
                    parkingLots = parkingLots,
                    isLoading = false
                )
            }
        }
    }
}

data class ParkingSearchUiState(
    val parkingLots: List<ParkingLot> = emptyList(),
    val parkingNameText: String = "",
    val isValidParkingNameText: Boolean = false,
    val cityText: String = "",
    val isValidCityText: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessageId: Int? = null
)