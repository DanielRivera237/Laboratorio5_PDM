package com.falconteam.laboratorio5.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.falconteam.laboratorio5.models.Card
import com.falconteam.laboratorio5.navigation.Screen
import com.falconteam.laboratorio5.ui.components.Cards
import com.falconteam.laboratorio5.viewmodel.ToDoViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoScreen(
    navController: NavController,
    viewModel: ToDoViewModel = viewModel()
) {
    val tasks by viewModel.tasks.collectAsState()
    val openDialog = remember { mutableStateOf(false) }
    val newCard = remember { mutableStateOf(Card(0, "", "")) }
    val openDateDialog = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("To Do") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Home.route)
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { openDialog.value = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.Black)
            }
        },
    ) { padding ->
        if (openDialog.value) {
            Dialog(
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                ),
                onDismissRequest = { openDialog.value = false },
                content = {
                    Card {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Agregar tarea",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                textAlign = TextAlign.Center,
                            )
                            TextField(
                                value = newCard.value.title,
                                onValueChange = { newCard.value = newCard.value.copy(title = it) },
                                label = { Text("Title") },
                                modifier = Modifier.padding(8.dp),
                            )
                            TextField(
                                value = newCard.value.description,
                                onValueChange = {
                                    newCard.value = newCard.value.copy(description = it)
                                },
                                label = { Text("Description") },
                                modifier = Modifier.padding(8.dp)
                            )
                            TextField(
                                value = newCard.value.endDate.toString(),
                                readOnly = true,
                                onValueChange = {},
                                label = { Text("Fecha") },
                                modifier = Modifier.padding(8.dp)
                            )
                            Button(
                                onClick = {
                                    openDateDialog.value = true
                                },
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text("Select Date")
                            }
                            if (openDateDialog.value) {
                                val datePickerState = rememberDatePickerState(
                                    initialSelectedDateMillis = Date().time
                                )
                                DatePickerDialog(
                                    onDismissRequest = {
                                        openDateDialog.value = false
                                    },
                                    confirmButton = {
                                        Button(onClick = {
                                            openDateDialog.value = false
                                            newCard.value = newCard.value.copy(
                                                endDate = datePickerState.selectedDateMillis?.let {
                                                    Date(it)
                                                } ?: Date()
                                            )
                                        }) {
                                            Text("OK")
                                        }
                                    },
                                ) {
                                    DatePicker(state = datePickerState)
                                }
                            }
                            Button(
                                onClick = {
                                    viewModel.addTask(newCard.value)
                                    newCard.value = Card(0, "", "")
                                    openDialog.value = false
                                },
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text("Add")
                            }
                        }
                    }
                }
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = padding.calculateTopPadding() + 16.dp),
        ) {
            itemsIndexed(tasks) { index, card ->
                Cards(
                    card = card,
                    pos = index,
                    max = tasks.size - 1,
                    delete = { pos -> viewModel.removeTask(pos) },
                    check = { checked, pos -> viewModel.toggleTask(pos, checked) },
                    changePosition = { from, to -> viewModel.moveTask(from, to) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
