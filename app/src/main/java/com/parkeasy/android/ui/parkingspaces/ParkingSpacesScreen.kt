package com.parkeasy.android.ui.parkingspaces

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.parkeasy.android.R
import com.parkeasy.android.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ParkingSpacesScreen(
    viewModel: ParkingSpacesViewModel = hiltViewModel(),
    navController: NavController,
    parkingLotId: String
) {
    val uiState = viewModel.uiState
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    LaunchedEffect(key1 = true) {
        viewModel.init(parkingLotId)
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = stringResource(R.string.parking_spaces_title))
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (uiState.isLoadingData) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            } else {
                Text(text = uiState.parkingLot.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = stringResource(
                        id = R.string.parking_spaces_number,
                        uiState.parkingSpaces.count()
                    ),
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(modifier = Modifier.weight(1f)) {
                    item {
                        FlowRow(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(
                                space = 10.dp,
                                alignment = Alignment.CenterHorizontally
                            )
                        ) {
                            uiState.parkingSpaces.forEach { parkingSpace ->
                                val reservedByCurrentUser =
                                    uiState.userReservations.any { it.parkingSpaceId == parkingSpace.id }

                                if (uiState.selectedParkingSpace == parkingSpace) {
                                    Button(
                                        onClick = {
                                            viewModel.onSelectedParkingSpaceChanged(
                                                parkingSpace
                                            )
                                        },
                                        colors = if (reservedByCurrentUser) ButtonDefaults.buttonColors(
                                            containerColor = Color.Green
                                        ) else ButtonDefaults.buttonColors(),
                                        shape = CircleShape
                                    ) {
                                        Text(text = parkingSpace.number.toString())
                                    }
                                } else {
                                    val border = if (reservedByCurrentUser) BorderStroke(
                                        width = 1.dp,
                                        color = Color.Green
                                    ) else ButtonDefaults.outlinedButtonBorder

                                    OutlinedButton(
                                        onClick = {
                                            viewModel.onSelectedParkingSpaceChanged(parkingSpace)
                                        },
                                        border = border,
                                        shape = CircleShape
                                    ) {
                                        Text(text = parkingSpace.number.toString())
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                if (uiState.selectedParkingSpace != null && uiState.userReservations.any { it.parkingSpaceId == uiState.selectedParkingSpace.id }) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = {}, modifier = Modifier.weight(1f)) {
                            Text(text = stringResource(id = R.string.parking_spaces_reserve_edit_button))
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Button(
                            onClick = { viewModel.onCancelReservationClick() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = stringResource(id = R.string.parking_spaces_reserve_cancel_button))
                        }
                    }
                } else {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { navController.navigate("${Screen.Reservation.route}/${uiState.parkingLot.id}/${uiState.selectedParkingSpace!!.id}") },
                            enabled = uiState.selectedParkingSpace != null && !uiState.isReserveLoading,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = stringResource(id = R.string.parking_spaces_reserve_button))
                        }

                        if (uiState.currentUser.admin) {
                            Spacer(modifier = Modifier.width(10.dp))

                            TextButton(
                                onClick = { viewModel.onShowAddParkingSpacesDialogChanged() },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "Add")
                            }
                        }
                    }
                }
            }
        }
    }

    if (uiState.showAddParkingSpacesDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onShowAddParkingSpacesDialogChanged() },
            title = {
                Text(text = "Add parking spaces")
            },
            text = {
                TextField(
                    value = uiState.parkingSpacesNumber,
                    onValueChange = { viewModel.onParkingSpacesNumberChanged(it) },
                    label = {
                        Text(text = "Number")
                    }
                )
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.onAddParkingSpacesClick() },
                    enabled = uiState.parkingSpacesNumber.toIntOrNull() != null
                ) {
                    Text(text = "Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onShowAddParkingSpacesDialogChanged() }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}