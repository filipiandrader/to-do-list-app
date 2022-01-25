package com.far.to_dolistapp.ui.list.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.far.to_dolistapp.data.Todo
import com.far.to_dolistapp.ui.components.CustomDialog
import com.far.to_dolistapp.ui.list.event.TodoListEvent

@Composable
fun TodoItem(
    todo: Todo,
    onEventDelete: (TodoListEvent.OnDeleteTodoClick) -> Unit,
    onEventCheck: (TodoListEvent.OnDoneChangeClick) -> Unit,
    modifier: Modifier = Modifier
) {
    val openDialogDelete = remember { mutableStateOf(false) }
    val openDialogCheck = remember { mutableStateOf(false) }
    val isCheckedState = remember { mutableStateOf(todo.isDone) }
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = todo.title,
                        style = MaterialTheme.typography.body1,
                    )
                }
                todo.description?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = { isChecked ->
                    isCheckedState.value = isChecked
                    openDialogCheck.value = true
                }
            )
            IconButton(
                onClick = { openDialogDelete.value = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
    if (openDialogDelete.value) {
        CustomDialog(
            onEvent = { onEventDelete(TodoListEvent.OnDeleteTodoClick(todo)) },
            title = "Warning!",
            textContent = "Are you sure you want to delete this item?"
        ) {
            openDialogDelete.value = false
        }
    }
    if (openDialogCheck.value) {
        CustomDialog(
            onEvent = { onEventCheck(TodoListEvent.OnDoneChangeClick(todo, isCheckedState.value)) },
            title = "Warning!",
            textContent = "Have you ever done this ToDo?"
        ) {
            openDialogCheck.value = false
        }
    }
}