package com.jdra_lab5.laboratorio6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Libro(val nombre: String, val editor: String)

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
    val libros = remember { mutableStateOf<List<Libro>>(emptyList()) }
    val isLoading = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(title = { Text("Laboratorio 6") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (isLoading.value) {
                        // Si ya está cargando, mostrar mensaje
                        scope.launch {
                            snackbarHostState.showSnackbar("La carga ya está en proceso...")
                        }
                    } else {
                        isLoading.value = true
                        libros.value = emptyList()
                        scope.launch {
                            delay(3000)
                            libros.value = List(8) {
                                Libro("Luna de plutón", "Dross")
                            }
                            isLoading.value = false
                            snackbarHostState.showSnackbar("Datos cargados correctamente")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Get Data")
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                isLoading.value -> {
                    CircularProgressIndicator()
                }
                libros.value.isEmpty() -> {
                    Text("Sin datos que mostrar")
                }
                else -> {
                    LazyColumn {
                        items(libros.value) { libro ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Nombre del libro: ${libro.nombre}")
                                    Text("Nombre del editor: ${libro.editor}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}