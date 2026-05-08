package com.example.futboleros.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.futboleros.MainActivity
import com.example.futboleros.data.AppDataBase
import com.example.futboleros.data.entity.ResultadosRepository
import com.example.futboleros.screens.AddResultScreen
import com.example.futboleros.screens.AddResultViewModel
import com.example.futboleros.screens.FixtureScreen
import com.example.futboleros.screens.FixtureViewModel
import com.example.futboleros.screens.LoginScreen
import com.example.futboleros.screens.LoginViewModel
import com.example.futboleros.screens.Pantalla1
import com.example.futboleros.screens.SplashScreen
import com.example.futboleros.screens.Torneo1
import com.example.futboleros.screens.TorneoViewModel


// Aquí se lleva a cabo la navegación entre pantallas.
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val database = AppDataBase.getDataBase(context)
    val repository = ResultadosRepository(database.resultadosDao())

    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route){

        composable(route = AppScreens.SplashScreen.route){
            SplashScreen(navController)
        }

        composable(route = AppScreens.MainActivity.route){
            MainActivity(navController)
        }

        composable(route = AppScreens.Pantalla1.route){
            Pantalla1(navController)
        }

        composable(route = AppScreens.Login.route) {
            val loginViewModel: LoginViewModel = viewModel(
                factory = LoginViewModel.Factory(database.userDao())
            )
            LoginScreen(navController, loginViewModel)
        }


        composable(route = AppScreens.Torneo1.route) {
            val torneoViewModel: TorneoViewModel = viewModel(
                factory = TorneoViewModel.Factory(repository)
            )
            Torneo1(navController, torneoViewModel)
        }

        composable(route = AppScreens.Fixture.route) {
            val viewModel: FixtureViewModel = viewModel(
                factory = FixtureViewModel.Factory(repository)
            )
            FixtureScreen(navController, viewModel)
        }

        composable(route = AppScreens.AddResult.route) {
            val viewModel: AddResultViewModel = viewModel(
                factory = AddResultViewModel.Factory(repository)
            )
            AddResultScreen(navController, viewModel)
        }
    }
}
