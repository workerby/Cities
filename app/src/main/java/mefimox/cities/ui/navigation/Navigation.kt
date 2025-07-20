package mefimox.cities.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import mefimox.cities.ui.elements.MainScreen
import mefimox.cities.ui.elements.NewCitiesListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destination.MAIN.route) {
        composable(route = Destination.MAIN.route) {
            MainScreen()
        }

        composable(route = Destination.NEW_LIST.route) {
            NewCitiesListScreen()
        }
    }

    LaunchedEffect(Unit) {
        launch {
            navigationFlow.collect {
                if (it == Destination.BACK.route) {
                    navController.popBackStack()
                } else {
                    navController.navigate(it)
                }
            }
        }
    }
}
