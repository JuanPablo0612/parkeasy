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

/**
 * ParkingLotSearchViewModel class.
 */
@HiltViewModel
class ParkingLotSearchViewModel @Inject constructor(
    private val searchParkingLotsByNameUseCase: SearchParkingLotsByNameUseCase,
    private val searchParkingLotsByCityUseCase: SearchParkingLotsByCityUseCase
) : ViewModel() {
    var uiState by mutableStateOf(ParkingSearchUiState())
        private set

    /**
     * Updates the UI state with the new parking name text.
     *
     * @param text The new parking name text entered by the user.
     */
    fun onParkingNameTextChanged(text: String) {
        uiState = uiState.copy(parkingNameText = text)
    }

    /**
     * Validates the parking name text entered by the user.
     *
     * This method updates the [uiState] by setting the [isValidParkingNameText] flag based on whether the [parkingNameText] is empty or not.
     *
     * @see [ParkingLotSearchViewModel.uiState]
     * @see [ParkingLotSearchViewModel.uiState.parkingNameText]
     * @see [ParkingLotSearchViewModel.uiState.isValidParkingNameText]
     */
    fun validateParkingNameText() {
        uiState = uiState.copy(isValidParkingNameText = uiState.parkingNameText.isNotEmpty())
    }

    /**
     * Updates the UI state with the new text entered in the city field.
     *
     * @param text The new text entered in the city field.
     */
    fun onCityTextChanged(text: String) {
        uiState = uiState.copy(cityText = text)
    }

    /**
     * Validates the city text entered by the user.
     * Updates the [uiState] by setting the [isValidCityText] flag based on whether the [cityText] is empty or not.
     */
    fun validateCityText() {
        uiState = uiState.copy(isValidCityText = uiState.cityText.isNotEmpty())
    }

    /**
     * Performs a search for parking lots based on the provided parking name text.
     * Updates the UI state to indicate that the search is in progress.
     * Collects the search results using the [searchParkingLotsByNameUseCase] use case.
     * Updates the UI state with the retrieved parking lots and sets the isLoading flag to false.
     */
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

/**
 * ParkingSearchUiState class.
 */
data class ParkingSearchUiState(
    val parkingLots: List<ParkingLot> = emptyList(),
    val parkingNameText: String = "",
    val isValidParkingNameText: Boolean = false,
    val cityText: String = "",
    val isValidCityText: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessageId: Int? = null
)