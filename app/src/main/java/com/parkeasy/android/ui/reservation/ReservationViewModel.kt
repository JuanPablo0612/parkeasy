package com.parkeasy.android.ui.reservation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.parkeasy.android.data.exceptions.getMessageId
import com.parkeasy.android.domain.model.parkinglots.ParkingLot
import com.parkeasy.android.domain.model.parkingspaces.ParkingSpace
import com.parkeasy.android.domain.model.users.User
import com.parkeasy.android.domain.usecase.parkinglots.GetParkingLotByIdUseCase
import com.parkeasy.android.domain.usecase.reservations.AddReservationUseCase
import com.parkeasy.android.domain.usecase.reservations.CheckReservationAvailabilityUseCase
import com.parkeasy.android.domain.usecase.reservations.GetParkingSpaceByIdUseCase
import com.parkeasy.android.domain.usecase.users.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getParkingLotByIdUseCase: GetParkingLotByIdUseCase,
    private val getParkingSpaceByIdUseCase: GetParkingSpaceByIdUseCase,
    private val checkReservationAvailabilityUseCase: CheckReservationAvailabilityUseCase,
    private val addReservationUseCase: AddReservationUseCase
) : ViewModel() {
    var uiState by mutableStateOf(ReservationUiState())
        private set

    fun init(parkingLotId: String, parkingSpaceId: String) {
        viewModelScope.launch {
            combine(
                getCurrentUserUseCase(),
                getParkingLotByIdUseCase(parkingLotId),
                getParkingSpaceByIdUseCase(parkingSpaceId)
            ) { user, parkingLot, parkingSpace ->
                uiState.copy(
                    currentUser = user,
                    parkingLot = parkingLot,
                    parkingSpace = parkingSpace
                )
            }.collect {
                uiState = it
            }
        }
    }

    fun resetReservationResults() {
        uiState = uiState.copy(
            showReserveDialog = false,
            isReserveSuccess = null,
            errorMessageId = null
        )
    }

    fun onCheckAvailabilityClick(
        startDateMillis: Long,
        endDateMillis: Long,
        startHour: Int,
        startMinute: Int,
        endHour: Int,
        endMinute: Int
    ) {
        viewModelScope.launch {
            uiState = uiState.copy(isCheckingAvailability = true)

            val timeZoneOffset = TimeZone.getDefault().getOffset(startDateMillis)
            val startDate = Date((startDateMillis + (startHour * 3600 + startMinute * 60) * 1000) - timeZoneOffset)
            val endDate = Date((endDateMillis + (endHour * 3600 + endMinute * 60) * 1000) - timeZoneOffset)

            val startTimestamp = Timestamp(startDate)
            val endTimestamp = Timestamp(endDate)

            val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
            val startString = sdf.format(startDate)
            val endString = sdf.format(endDate)

            uiState = try {
                val isAvailable = checkReservationAvailabilityUseCase(
                    uiState.parkingSpace.id,
                    startTimestamp,
                    endTimestamp
                )

                uiState.copy(
                    startTimestamp = startTimestamp,
                    endTimestamp = endTimestamp,
                    startText = startString,
                    endText = endString,
                    isReserveAvailable = isAvailable,
                    showReserveDialog = isAvailable
                )
            } catch (e: Exception) {
                uiState.copy(errorMessageId = e.getMessageId())
            }

            uiState = uiState.copy(isCheckingAvailability = false)
        }
    }

    fun onReserveClick() {
        viewModelScope.launch {
            uiState = uiState.copy(showReserveDialog = false, isReserving = true)

            uiState = try {
                addReservationUseCase(
                    uiState.currentUser.id,
                    uiState.parkingLot.id,
                    uiState.parkingSpace.id,
                    uiState.startTimestamp,
                    uiState.endTimestamp
                )

                uiState.copy(isReserveSuccess = true)
            } catch (e: Exception) {
                uiState.copy(errorMessageId = e.getMessageId())
            }

            uiState = uiState.copy(isReserving = false)
        }
    }
}

data class ReservationUiState(
    val currentUser: User = User(),
    val parkingLot: ParkingLot = ParkingLot(),
    val parkingSpace: ParkingSpace = ParkingSpace(),
    val startTimestamp: Timestamp = Timestamp.now(),
    val endTimestamp: Timestamp = Timestamp.now(),
    val startText: String = "",
    val endText: String = "",
    val isCheckingAvailability: Boolean = false,
    val isReserveAvailable: Boolean? = null,
    val showReserveDialog: Boolean = false,
    val isReserving: Boolean = false,
    val isReserveSuccess: Boolean? = null,
    val errorMessageId: Int? = null
)