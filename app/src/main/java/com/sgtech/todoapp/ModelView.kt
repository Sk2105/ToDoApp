package com.sgtech.todoapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModelView : ViewModel() {
    private var todoList = mutableStateListOf<ToDo>()

    val todoListLiveData = todoList

    fun addTodo(todo: ToDo) {
        todoList.add(todo)
    }

    fun isDone(todo: ToDo) {
        todoList.map {
            if (todo.name == it.name) {
                it.done = todo.done
            }
        }
    }
}