package com.parkeasy.android.ui.parkinglotslist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkeasy.android.domain.model.parkinglots.ParkingLot
import com.parkeasy.android.domain.model.parkingspaces.ParkingSpace
import com.parkeasy.android.domain.usecase.parkinglots.GetAllParkingLotsUseCase
import com.parkeasy.android.domain.usecase.parkingspaces.GetAllParkingSpacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParkingLotsViewModel @Inject constructor(
    private val getAllParkingLotsUseCase: GetAllParkingLotsUseCase,
    private val getAllParkingSpacesUseCase: GetAllParkingSpacesUseCase
) : ViewModel() {
    var uiState by mutableStateOf(ParkingLotsUiState())
        private set

    init {
        getAllParkingLots()
        getAllParkingSpaces()
    }

    private fun getAllParkingLots() {
        viewModelScope.launch {
            getAllParkingLotsUseCase().collect { parkingLots ->
                uiState = uiState.copy(parkingLots = parkingLots.sortedBy { it.name })
            }
        }
    }

    private fun getAllParkingSpaces() {
        viewModelScope.launch {
            getAllParkingSpacesUseCase().collect { parkingSpaces ->
                uiState = uiState.copy(parkingSpaces = parkingSpaces)
            }
        }
    }
}

data class ParkingLotsUiState(
    val parkingLots: List<ParkingLot> = emptyList(),
    val parkingSpaces: List<ParkingSpace> = emptyList()
)