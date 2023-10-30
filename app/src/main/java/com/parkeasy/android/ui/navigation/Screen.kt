package com.parkeasy.android.ui.navigation

/**
 * Screen class.
 */
sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")
    object ParkingLots : Screen("parking_lots")
    object ParkingSpaces : Screen("parking_spaces")
    object Reservation : Screen("reservation")
}