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

/**
 * ParkingLotsViewModel class.
 */
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

    /**
     * Retrieves all parking lots and updates the UI state with the sorted list of parking lots.
     *
     * This method is a coroutine function that runs in the viewModelScope. It launches a new coroutine
     * to execute the [getAllParkingLotsUseCase] and collects the emitted parking lots. The collected
     * parking lots are then sorted by their names and assigned to the [uiState] property of the
     * ViewModel.
     *
     * @see getAllParkingLotsUseCase
     * @see uiState
     */
    private fun getAllParkingLots() {
        viewModelScope.launch {
            getAllParkingLotsUseCase().collect { parkingLots ->
                uiState = uiState.copy(parkingLots = parkingLots.sortedBy { it.name })
            }
        }
    }

    /**
     * Retrieves all parking spaces and updates the UI state with the collected data.
     *
     * This method is a coroutine function that runs in the [viewModelScope]. It launches a new coroutine
     * to execute the [getAllParkingSpacesUseCase] and collects the emitted parking spaces. The collected
     * parking spaces are then assigned to the [uiState] property by creating a new copy of the [uiState]
     * object with the updated parking spaces.
     *
     * @see viewModelScope
     * @see getAllParkingSpacesUseCase
     * @see uiState
     */
    private fun getAllParkingSpaces() {
        viewModelScope.launch {
            getAllParkingSpacesUseCase().collect { parkingSpaces ->
                uiState = uiState.copy(parkingSpaces = parkingSpaces)
            }
        }
    }
}

/**
 * ParkingLotsUiState class.
 */
data class ParkingLotsUiState(
    val parkingLots: List<ParkingLot> = emptyList(),
    val parkingSpaces: List<ParkingSpace> = emptyList()
)