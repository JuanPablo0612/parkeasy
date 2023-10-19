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

    fun onSelectedParkingSpaceChanged(parkingSpace: ParkingSpace) {
        val selectedParkingSpace = if (parkingSpace == uiState.selectedParkingSpace) null else parkingSpace
        uiState = uiState.copy(selectedParkingSpace = selectedParkingSpace)
    }

    fun onShowAddParkingSpacesDialogChanged() {
        uiState = uiState.copy(showAddParkingSpacesDialog = !uiState.showAddParkingSpacesDialog)
    }

    fun onParkingSpacesNumberChanged(number: String) {
        uiState = uiState.copy(parkingSpacesNumber = number)
    }

    fun onAddParkingSpacesClick() {
        viewModelScope.launch {
            val initialCount = uiState.parkingSpaces.count() + 1
            val finalCount = initialCount + uiState.parkingSpacesNumber.toInt() - 1

            (initialCount..finalCount).forEach {
                addParkingSpaceUseCase(uiState.parkingLot.id, it)
            }
        }
    }

    fun onCancelReservationClick() {
        viewModelScope.launch {
            deleteReservationUseCase(uiState.selectedParkingSpace!!.id)
        }
    }
}

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