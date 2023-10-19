package com.parkeasy.android.ui.main

import android.Manifest
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.parkeasy.android.R
import com.parkeasy.android.ui.account.AccountScreen
import com.parkeasy.android.ui.home.HomeScreen
import com.parkeasy.android.ui.parkinglotslist.ParkingLotsScreen

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(navController: NavController) {
    val tabsNavController = rememberNavController()
    val selectedTabIndex =
        tabsNavController.currentBackStackEntryAsState().value?.destination?.route
            ?.toIntOrNull() ?: 0

    val tabs = listOf(
        TabItem.Home,
        TabItem.List,
        TabItem.Account
    )

    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val hasPermission = permissionsState.allPermissionsGranted

    Scaffold(
        bottomBar = {
            if (hasPermission) {
                NavigationBar {
                    tabs.forEach { tabItem ->
                        NavigationBarItem(
                            selected = selectedTabIndex == tabItem.index,
                            icon = {
                                Icon(
                                    imageVector = tabItem.icon,
                                    contentDescription = null
                                )
                            },
                            label = { Text(text = stringResource(id = tabItem.titleId)) },
                            onClick = {
                                tabsNavController.navigate(tabItem.index.toString()) {
                                    popUpTo(tabsNavController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (hasPermission) {
                NavHost(navController = tabsNavController, startDestination = "0") {
                    tabs.forEach { tabItem ->
                        composable(
                            route = tabItem.index.toString(),
                            enterTransition = {
                                slideInHorizontally { height ->
                                    if (initialState.destination.route!!.toInt() <= tabItem.index) height else -height
                                }
                            },
                            exitTransition = {
                                slideOutHorizontally { height ->
                                    if (targetState.destination.route!!.toInt() <= tabItem.index) height else -height
                                }
                            },
                            content = {
                                when (tabItem) {
                                    TabItem.Home -> {
                                        HomeScreen(onSettingsClick = {
                                            tabsNavController.navigate(TabItem.Account.index.toString()) {
                                                popUpTo(tabsNavController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        })
                                    }

                                    TabItem.List -> {
                                        ParkingLotsScreen(navController = navController)
                                    }

                                    TabItem.Account -> {
                                        AccountScreen(navController = navController)
                                    }
                                }
                            }
                        )
                    }
                }
            } else {
                PermissionsRequest(onClick = { permissionsState.launchMultiplePermissionRequest() })
            }
        }
    }
}

sealed class TabItem(val index: Int, val titleId: Int, val icon: ImageVector) {
    object Home : TabItem(0, R.string.main_tab_home, Icons.Default.Home)
    object List : TabItem(1, R.string.main_tab_list, Icons.Default.List)
    object Account : TabItem(2, R.string.main_tab_account, Icons.Default.Person)
}