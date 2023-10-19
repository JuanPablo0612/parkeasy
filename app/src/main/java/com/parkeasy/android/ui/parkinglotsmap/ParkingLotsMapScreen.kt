package com.parkeasy.android.ui.parkinglotsmap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.parkeasy.android.R
import com.parkeasy.android.ui.common.AutoSizeText
import com.parkeasy.android.ui.map.Map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingLotsMapScreen(
    viewModel: ParkingLotsMapViewModel = hiltViewModel(),
    onSettingsClick: () -> Unit
) {
    val uiState = viewModel.uiState
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .height(150.dp)
                                .padding(8.dp)
                                .align(Alignment.Center)
                                .alpha(1f - scrollBehavior.state.collapsedFraction)
                        ) {
                            AutoSizeText(
                                text = "${uiState.currentUser.firstName} ${uiState.currentUser.lastName}",
                                style = MaterialTheme.typography.titleLarge,
                                maxFontSize = MaterialTheme.typography.titleLarge.fontSize
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            AutoSizeText(
                                text = uiState.currentUser.email,
                                style = MaterialTheme.typography.titleSmall,
                                maxFontSize = MaterialTheme.typography.titleSmall.fontSize
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(50.dp)
                                .align(Alignment.CenterStart)
                                .padding(start = 16.dp)
                                .alpha(scrollBehavior.state.collapsedFraction)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.fillMaxHeight(0.8f)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            AutoSizeText(
                                text = stringResource(
                                    id = R.string.parking_lots_map_title,
                                    uiState.currentUser.firstName
                                ),
                                style = MaterialTheme.typography.titleLarge,
                                maxFontSize = MaterialTheme.typography.titleLarge.fontSize
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                    }
                },
                scrollBehavior = scrollBehavior,
                modifier = Modifier.heightIn(50.dp, 200.dp)
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Map(modifier = Modifier.fillMaxSize())
        }
    }
}