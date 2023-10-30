package com.parkeasy.android.ui.parkingspaces

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkeasy.android.domain.model.parkinglots.ParkingLot
import com.parkeasy.android.domain.model.parkingspaces.ParkingSpace
import com.parkeasy.android.domain.model.reservations.Reservation
import com.parkeasy.android.domain.model.users.User
import com.parkeasy.android.domain.usecase.parkinglots.GetParkingLotByIdUseCase
import com.parkeasy.android.domain.usecase.parkingspaces.AddParkingSpaceUseCase
import com.parkeasy.android.domain.usecase.parkingspaces.GetParkingSpacesByParkingLotUseCase
import com.parkeasy.android.domain.usecase.reservations.DeleteReservationUseCase
import com.parkeasy.android.domain.usecase.reservations.GetReservationsByParkingLotUseCase
import com.parkeasy.android.domain.usecase.users.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ParkingSpacesViewModel class.
 */
@HiltViewModel
class ParkingSpacesViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getParkingLotByIdUseCase: GetParkingLotByIdUseCase,
    private val getParkingSpacesByParkingLotUseCase: GetParkingSpacesByParkingLotUseCase,
    private val addParkingSpaceUseCase: AddParkingSpaceUseCase,
    private val getReservationsByParkingLotUseCase: GetReservationsByParkingLotUseCase,
    private val deleteReservationUseCase: DeleteReservationUseCase
) : ViewModel() {
    var uiState by mutableStateOf(ParkingSpacesUiState())
        private set

    /**
     * Initializes the parking spaces view model with the provided parking lot ID.
     *
     * @param parkingLotId The ID of the parking lot to retrieve data for.
     */
    fun init(parkingLotId: String) {
        viewModelScope.launch {
            delay(300)

            combine(
                getCurrentUserUseCase(),
                getParkingLotByIdUseCase(parkingLotId),
                getParkingSpacesByParkingLotUseCase(parkingLotId),
                getReservationsByParkingLotUseCase(parkingLotId)
            ) { user, parkingLot, parkingSpaces, reservations ->
                uiState.copy(
                    currentUser = user,
                    parkingLot = parkingLot,
                    parkingSpaces = parkingSpaces.sortedBy { it.number },
                    reservations = reservations,
                    userReservations = reservations.filter { it.userId == user.id },
                    isLoadingData = false
                )
            }.collect {
                uiState = it
            }
        }
    }

    /**
     * Updates the selected parking space in the UI state.
     *
     * @param parkingSpace The new selected parking space.
     */
    fun onSelectedParkingSpaceChanged(parkingSpace: ParkingSpace) {
        val selectedParkingSpace =
            if (parkingSpace == uiState.selectedParkingSpace) null else parkingSpace
        uiState = uiState.copy(selectedParkingSpace = selectedParkingSpace)
    }

    /**
     * Toggles the visibility of the "Add Parking Spaces" dialog in the UI.
     *
     * This method updates the [uiState] property of the [ParkingSpacesViewModel] class by setting the value of
     * [showAddParkingSpacesDialog] to its opposite value.
     *
     * @see ParkingSpacesViewModel.uiState
     * @see ParkingSpacesViewModel.showAddParkingSpacesDialog
     */
    fun onShowAddParkingSpacesDialogChanged() {
        uiState = uiState.copy(showAddParkingSpacesDialog = !uiState.showAddParkingSpacesDialog)
    }

    /**
     * Updates the UI state when the number of parking spaces is changed.
     *
     * @param number The new number of parking spaces as a String.
     */
    fun onParkingSpacesNumberChanged(number: String) {
        uiState = uiState.copy(parkingSpacesNumber = number)
    }

    /**
     * Function to handle the click event when adding parking spaces.
     * This function is executed in a coroutine scope.
     *
     * It retrieves the initial count of parking spaces from the UI state and adds 1 to it.
     * Then, it calculates the final count by adding the parsed integer value of the parkingSpacesNumber from the UI state to the initial count minus 1.
     *
     * It iterates through the range of numbers from initialCount to finalCount and calls the addParkingSpaceUseCase function for each number,
     * passing the parking lot ID from the UI state and the current number as arguments.
     */
    fun onAddParkingSpacesClick() {
        viewModelScope.launch {
            val initialCount = uiState.parkingSpaces.count() + 1
            val finalCount = initialCount + uiState.parkingSpacesNumber.toInt() - 1

            (initialCount..finalCount).forEach {
                addParkingSpaceUseCase(uiState.parkingLot.id, it)
            }
        }
    }

    /**
     * Executes the cancellation of a reservation when the cancel reservation button is clicked.
     * This method is called within the [viewModelScope] coroutine scope.
     * It launches a coroutine to delete the reservation associated with the selected parking space.
     *
     * @throws [NullPointerException] if [uiState.selectedParkingSpace] is null.
     *
     * @see [deleteReservationUseCase]
     * @see [viewModelScope]
     * @see [uiState.selectedParkingSpace]
     */
    fun onCancelReservationClick() {
        viewModelScope.launch {
            deleteReservationUseCase(uiState.selectedParkingSpace!!.id)
        }
    }
}

/**
 * ParkingSpacesUiState class.
 */
data class ParkingSpacesUiState(
    val currentUser: User = User(),
    val parkingLot: ParkingLot = ParkingLot(),
    val parkingSpaces: List<ParkingSpace> = emptyList(),
    val reservations: List<Reservation> = emptyList(),
    val userReservations: List<Reservation> = emptyList(),
    val isLoadingData: Boolean = true,
    val selectedParkingSpace: ParkingSpace? = null,
    val isReserveLoading: Boolean = false,
    val isReserveSuccess: Boolean? = null,
    val errorMessageId: Int? = null,
    val showAddParkingSpacesDialog: Boolean = false,
    val parkingSpacesNumber: String = ""
)