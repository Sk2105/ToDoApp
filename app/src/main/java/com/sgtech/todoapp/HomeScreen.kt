package com.sgtech.todoapp

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: ModelView) {

    val dialogState = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "ToDo App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = Color.White,
                    containerColor = Color(0xff212121)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    dialogState.value = true
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
        ) {
            items(viewModel.todoListLiveData.size) {
                ToDoItem(toDo = viewModel.todoListLiveData[it], viewModel = viewModel)
            }

        }


    }

    if (dialogState.value) {
        AddTodo(modelView = viewModel, dialogState = dialogState)
    }
}


@Composable
fun ToDoItem(toDo: ToDo, viewModel: ModelView) {
    val isChecked = remember { mutableStateOf(toDo.done) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(BorderStroke(2.dp, Color.White), shape = RoundedCornerShape(10.dp))
    ) {
        Checkbox(checked = isChecked.value, onCheckedChange = {
            isChecked.value = !isChecked.value
            toDo.done = isChecked.value
            viewModel.isDone(toDo)
        })
        Text(text = toDo.name)
    }
}


@Composable
fun AddTodo(modelView: ModelView, dialogState: MutableState<Boolean>) {
    val context = LocalContext.current
    val todoInput = remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = { dialogState.value = false }, confirmButton = {
        Button(onClick = {
            val toDo = ToDo(todoInput.value, false)
            modelView.addTodo(toDo)
            Toast.makeText(context, "SuccessFull Add", Toast.LENGTH_SHORT).show()
            dialogState.value = false
        }) {
            Text(text = "Add")
        }
    },
        title = { Text(text = "Add Todo") },
        text = {
            Column {
                OutlinedTextField(value = todoInput.value, onValueChange = {
                    todoInput.value = it
                }, label = {
                    Text(text = "Enter todo title")
                })
            }


        })
}