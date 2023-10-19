package com.parkeasy.android.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.parkeasy.android.ui.login.LoginScreen
import com.parkeasy.android.ui.main.MainScreen
import com.parkeasy.android.ui.parkinglotslist.ParkingLotsScreen
import com.parkeasy.android.ui.parkingspaces.ParkingSpacesScreen
import com.parkeasy.android.ui.register.RegisterScreen
import com.parkeasy.android.ui.reservation.ReservationScreen
import com.parkeasy.android.ui.welcome.WelcomeScreen

@Composable
fun Navigation(viewModel: NavigationViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState
    val navController = rememberNavController()

    val startDestination =
        if (uiState.isLoggedIn) Screen.Main.route else Screen.Welcome.route

    if (!uiState.isLoading) {
        NavHost(navController = navController, startDestination = startDestination) {
            composable(
                route = Screen.Welcome.route,
                enterTransition = {
                    slideInHorizontally { height -> height }
                },
                exitTransition = {
                    slideOutHorizontally { height -> -height }
                },
                popEnterTransition = {
                    slideInHorizontally { height -> -height }
                }
            ) {
                WelcomeScreen(navController = navController)
            }

            composable(
                route = Screen.Login.route,
                enterTransition = {
                    slideInHorizontally { height -> height }
                },
                exitTransition = {
                    slideOutHorizontally { height -> -height }
                },
                popEnterTransition = {
                    slideInHorizontally { height -> -height }
                },
                popExitTransition = {
                    slideOutHorizontally { height -> height }
                }
            ) {
                LoginScreen(navController = navController)
            }

            composable(
                route = Screen.Register.route,
                enterTransition = {
                    slideInHorizontally { height -> height }
                },
                exitTransition = {
                    slideOutHorizontally { height -> -height }
                },
                popEnterTransition = {
                    slideInHorizontally { height -> -height }
                },
                popExitTransition = {
                    slideOutHorizontally { height -> height }
                }
            ) {
                RegisterScreen(navController = navController)
            }

            composable(
                route = Screen.ParkingLots.route,
                enterTransition = {
                    slideInHorizontally { height -> height }
                },
                exitTransition = {
                    slideOutHorizontally { height -> -height }
                },
                popEnterTransition = {
                    slideInHorizontally { height -> -height }
                }
            ) {
                ParkingLotsScreen(navController = navController)
            }

            composable(
                route = "${Screen.ParkingSpaces.route}/{parkingId}",
                arguments = listOf(navArgument("parkingId") { type = NavType.StringType }),
                enterTransition = {
                    slideInHorizontally { height -> height }
                },
                exitTransition = {
                    slideOutHorizontally { height -> -height }
                },
                popEnterTransition = {
                    slideInHorizontally { height -> -height }
                },
                popExitTransition = {
                    slideOutHorizontally { height -> height }
                }
            ) {
                ParkingSpacesScreen(
                    navController = navController,
                    parkingLotId = it.arguments?.getString("parkingId") ?: ""
                )
            }

            composable(
                route = Screen.Main.route,
                enterTransition = {
                    slideInHorizontally { height -> height }
                },
                exitTransition = {
                    slideOutHorizontally { height -> -height }
                },
                popEnterTransition = {
                    slideInHorizontally { height -> -height }
                },
                popExitTransition = {
                    slideOutHorizontally { height -> height }
                }
            ) {
                MainScreen(navController = navController)
            }

            composable(
                route = "${Screen.Reservation.route}/{parkingLotId}/{parkingSpaceId}",
                arguments = listOf(
                    navArgument("parkingLotId") { type = NavType.StringType },
                    navArgument("parkingSpaceId") { type = NavType.StringType }
                ),
                enterTransition = {
                    slideInHorizontally { height -> height }
                },
                exitTransition = {
                    slideOutHorizontally { height -> -height }
                },
                popEnterTransition = {
                    slideInHorizontally { height -> -height }
                },
                popExitTransition = {
                    slideOutHorizontally { height -> height }
                }
            ) {
                ReservationScreen(
                    navController = navController,
                    parkingLotId = it.arguments?.getString("parkingLotId") ?: "",
                    parkingSpaceId = it.arguments?.getString("parkingSpaceId") ?: ""
                )
            }
        }
    }
}