package com.parkeasy.android.ui.parkinglotslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.parkeasy.android.R
import com.parkeasy.android.ui.common.AutoSizeText
import com.parkeasy.android.ui.navigation.Screen

/**
 * Composable function that displays the screen for viewing parking lots.
 *
 * @param viewModel The view model for managing the parking lots screen.
 * @param navController The navigation controller for navigating to other screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingLotsScreen(
    viewModel: ParkingLotsViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState = viewModel.uiState
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    AutoSizeText(
                        text = stringResource(id = R.string.parking_lots_title),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                items(uiState.parkingLots, key = { it.id }) { parkingLot ->
                    ParkingLotCard(
                        parkingLot = parkingLot,
                        parkingSpaces = uiState.parkingSpaces.filter { it.parkingLotId == parkingLot.id },
                        onClick = { navController.navigate("${Screen.ParkingSpaces.route}/${parkingLot.id}") }
                    )
                }
            }
        }
    }
}