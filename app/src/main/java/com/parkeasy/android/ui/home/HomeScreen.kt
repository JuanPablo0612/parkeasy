package com.parkeasy.android.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Composable function that represents the home screen of the application.
 *
 * @param viewModel The view model for the home screen.
 * @param onSettingsClick Callback function to handle the settings button click.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onSettingsClick: () -> Unit
) {
    val uiState = viewModel.uiState
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            with(uiState.currentUser) {
                HomeTopAppBar(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    scrollBehavior = scrollBehavior,
                    onSettingsClick = onSettingsClick
                )
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .verticalScroll(state = rememberScrollState())
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    UserReservationsCountCard(uiState.reservations.count())

                    Spacer(modifier = Modifier.width(10.dp))

                    UserReservationsParkingLotsCountCard(
                        uiState.reservations.map { it.parkingLotId }.toSet().count()
                    )
                }
            }
        }
    }
}