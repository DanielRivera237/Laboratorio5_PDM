package com.falconteam.laboratorio5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.falconteam.laboratorio5.navigation.AppNavigation
import com.falconteam.laboratorio5.ui.theme.Laboratorio3Theme
import com.falconteam.laboratorio5.viewmodel.ToDoViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp() {
    Laboratorio3Theme {
        val navController = rememberNavController()
        val viewModel: ToDoViewModel = viewModel()
        AppNavigation(navController = navController, viewModel = viewModel)
    }
}