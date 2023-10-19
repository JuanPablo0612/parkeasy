package com.parkeasy.android.ui.reservation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.parkeasy.android.R
import com.parkeasy.android.ui.common.AutoSizeText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationScreen(
    viewModel: ReservationViewModel = hiltViewModel(),
    navController: NavController,
    parkingLotId: String,
    parkingSpaceId: String
) {
    val uiState = viewModel.uiState
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val startDatePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    val endDatePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    val startTimePickerState = rememberTimePickerState()
    val endTimePickerState = rememberTimePickerState()

    LaunchedEffect(true) {
        viewModel.init(parkingLotId, parkingSpaceId)
    }

    LaunchedEffect(uiState.isReserveSuccess) {
        uiState.isReserveSuccess?.let {
            if (it) {
                navController.navigateUp()
            }
        }
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    AutoSizeText(
                        text = stringResource(
                            id = R.string.reservation_title,
                            uiState.parkingLot.name
                        ),
                        style = MaterialTheme.typography.titleLarge,
                        maxFontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (uiState.isReserving) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = stringResource(id = R.string.reservation_made_by_user_text),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        AutoSizeText(
                            text = "${uiState.currentUser.firstName} ${uiState.currentUser.lastName}",
                            maxFontSize = 24.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = stringResource(id = R.string.reservation_start_date),
                        fontSize = 16.sp
                    )

                    DatePicker(
                        startDatePickerState,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = stringResource(id = R.string.reservation_end_date),
                        fontSize = 16.sp
                    )

                    DatePicker(
                        endDatePickerState,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = stringResource(id = R.string.reservation_start_time),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    TimeInput(
                        startTimePickerState,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = stringResource(id = R.string.reservation_end_hour),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    TimeInput(
                        endTimePickerState,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    val enableButton =
                        startDatePickerState.selectedDateMillis != null && endDatePickerState.selectedDateMillis != null && !uiState.isCheckingAvailability

                    Button(
                        onClick = {
                            viewModel.onCheckAvailabilityClick(
                                startDatePickerState.selectedDateMillis!!,
                                endDatePickerState.selectedDateMillis!!,
                                startTimePickerState.hour,
                                startTimePickerState.minute,
                                endTimePickerState.hour,
                                endTimePickerState.minute
                            )
                        },
                        enabled = enableButton,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (uiState.isCheckingAvailability) {
                            CircularProgressIndicator()
                        } else {
                            Text(text = stringResource(id = R.string.reservation_check_availability_button))
                        }
                    }
                }
            }
        }
    }

    if (uiState.showReserveDialog) {
        ConfirmReservationDialog(
            parkingLotName = uiState.parkingLot.name,
            parkingSpaceNumber = uiState.parkingSpace.number,
            startText = uiState.startText,
            endText = uiState.endText,
            onConfirm = { viewModel.onReserveClick() },
            onDismiss = {
                viewModel.resetReservationResults()
            }
        )
    }

    uiState.errorMessageId?.let {
        AlertDialog(
            onDismissRequest = {},
            text = { Text(text = stringResource(id = it)) },
            confirmButton = {
                Button(onClick = { viewModel.resetReservationResults() }) {
                    Text(text = stringResource(id = android.R.string.ok))
                }
            }
        )
    }
}