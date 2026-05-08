package com.example.futboleros.navigation

//Estas son las pantallas que se van a navegar.
sealed class AppScreens( val route: String) {

    object SplashScreen: AppScreens("splash_screen")

    object MainActivity: AppScreens("main_activity")

    object Pantalla1: AppScreens("pantalla_1")

    object Login: AppScreens("login")

    object Torneo1: AppScreens("torneo_1")

    object Fixture: AppScreens("fixture")

    object AddResult: AppScreens("add_result")
}
