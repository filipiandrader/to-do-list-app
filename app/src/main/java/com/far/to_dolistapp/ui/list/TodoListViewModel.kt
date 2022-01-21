package com.far.to_dolistapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.far.to_dolistapp.data.Todo
import com.far.to_dolistapp.data.TodoRepository
import com.far.to_dolistapp.util.Routes.ADD_TODO
import com.far.to_dolistapp.util.Routes.EDIT_TODO
import com.far.to_dolistapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private val _uiEventChannel = Channel<UiEvent>()
    val uiEventChannel = _uiEventChannel.receiveAsFlow()

    val allTodos = repository.getTodos()
    private var deletedTodo: Todo? = null

    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.OnDeleteTodoClick -> deleteTodo(event.todo)
            is TodoListEvent.OnAddTodoClick -> sendUiEvent(UiEvent.Navigate(ADD_TODO))
            is TodoListEvent.OnDoneChangeClick -> insertOrUpdateTodo(event.todo.copy(isDone = event.isDone))
            is TodoListEvent.OnTodoClick -> sendUiEvent(UiEvent.Navigate(EDIT_TODO.plus(event.todo.id)))
            is TodoListEvent.OnUndoDeleteClick -> undoDeleteTodo()
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch { _uiEventChannel.send(uiEvent) }
    }

    private fun insertOrUpdateTodo(todo: Todo) {
        viewModelScope.launch { repository.insertTodo(todo) }
    }

    private fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            deletedTodo = todo
            repository.deleteTodo(todo)
            sendUiEvent(UiEvent.ShowSnackbar(
                message = "Todo deleted",
                action = "Undo"
            ))
        }
    }

    private fun undoDeleteTodo() {
        deletedTodo?.let { todo ->
            viewModelScope.launch { insertOrUpdateTodo(todo) }
        }
    }
}