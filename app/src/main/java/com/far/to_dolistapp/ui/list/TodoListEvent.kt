package com.far.to_dolistapp.ui.list

import com.far.to_dolistapp.data.Todo

sealed class TodoListEvent {

    data class OnDeleteTodoClick(
        val todo: Todo
    ) : TodoListEvent()

    data class OnDoneChangeClick(
        val todo: Todo,
        val isDone: Boolean
    ) : TodoListEvent()

    object OnUndoDeleteClick: TodoListEvent()

    data class  OnTodoClick(
        val todo: Todo
    ) : TodoListEvent()

    object OnAddTodoClick: TodoListEvent()
}
