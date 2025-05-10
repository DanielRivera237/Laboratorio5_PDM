package com.falconteam.laboratorio5.screens

import android.hardware.Sensor
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.falconteam.laboratorio5.navigation.Screen
import com.falconteam.laboratorio5.ui.components.useSensor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorScreen(navController: NavController) {
    val lightValues = useSensor(Sensor.TYPE_LIGHT)

    SensorScaffold(
        title = "Sensor de Luz",
        onBackClick = { navController.navigate(Screen.Home.route) }
    ) {
        SensorContent(intensity = lightValues[0])
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorScaffold(
    title: String,
    onBackClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = content
    )
}

@Composable
fun SensorContent(intensity: Float) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sensor de Luz", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Intensidad: $intensity lx", fontSize = 18.sp)
    }
}
