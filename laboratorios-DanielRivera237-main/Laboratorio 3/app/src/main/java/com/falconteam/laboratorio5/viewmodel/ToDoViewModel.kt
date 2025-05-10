package com.falconteam.laboratorio5.viewmodel

import androidx.lifecycle.ViewModel
import com.falconteam.laboratorio5.models.Card
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ToDoViewModel : ViewModel() {

    private val _tasks = MutableStateFlow<List<Card>>(emptyList())
    val tasks: StateFlow<List<Card>> = _tasks.asStateFlow()

    fun addTask(card: Card) {
        _tasks.value = _tasks.value + card
    }

    fun removeTask(index: Int) {
        _tasks.value = _tasks.value.toMutableList().apply { removeAt(index) }
    }

    fun toggleTask(index: Int, checked: Boolean) {
        _tasks.value = _tasks.value.toMutableList().apply {
            val card = get(index)
            set(index, card.copy(checked = checked))
        }
    }

    fun moveTask(from: Int, to: Int) {
        if (from !in _tasks.value.indices || to !in _tasks.value.indices) return
        val mutable = _tasks.value.toMutableList()
        val item = mutable.removeAt(from)
        mutable.add(to, item)
        _tasks.value = mutable
    }
}
