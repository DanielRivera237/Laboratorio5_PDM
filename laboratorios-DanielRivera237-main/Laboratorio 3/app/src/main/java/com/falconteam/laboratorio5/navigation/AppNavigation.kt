// 📁 navigation/AppNavigation.kt
package com.falconteam.laboratorio5.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.falconteam.laboratorio5.screens.HomeScreen
import com.falconteam.laboratorio5.screens.SensorScreen
import com.falconteam.laboratorio5.screens.ToDoScreen
import com.falconteam.laboratorio5.viewmodel.ToDoViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: ToDoViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Sensor.route) {
            SensorScreen(navController)
        }
        composable(Screen.Todo.route) {
            ToDoScreen(navController, viewModel)
        }
    }
}
