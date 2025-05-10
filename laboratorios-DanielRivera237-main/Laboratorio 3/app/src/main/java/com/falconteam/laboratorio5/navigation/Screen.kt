package com.falconteam.laboratorio5.navigation

sealed class Screen (val route: String){
    object Home: Screen("home")
    object Sensor: Screen("sensor")
    object Todo: Screen("todo")
}