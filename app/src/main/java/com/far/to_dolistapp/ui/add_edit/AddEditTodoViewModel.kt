package com.far.to_dolistapp.ui.add_edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.far.to_dolistapp.data.Todo
import com.far.to_dolistapp.data.TodoRepository
import com.far.to_dolistapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var todo by mutableStateOf<Todo?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiEventChannel = Channel<UiEvent>()
    val uiEventChannel = _uiEventChannel.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")

        if (todoId != -1) {
            viewModelScope.launch {
                todoId?.let {
                    repository.getTodoById(todoId)?.let {
                        todo = it
                        title = it.title
                        description = it.description.orEmpty()
                    }
                }
            }
        }
    }


    fun onEvent(event: AddEditTodoEvent) {
        when (event) {
            is AddEditTodoEvent.OnTitleChange -> title = event.title
            is AddEditTodoEvent.OnDescriptionChange -> description = event.description
            is AddEditTodoEvent.OnSaveTodoClick -> {
                if (title.isEmpty()) {
                    sendUiEvent(UiEvent.ShowSnackbar(message = "The title can't be empty"))
                    return
                }
                todo?.let { insertOrUpdateTodo(Todo(it.id, title, description, it.isDone)) }
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    private fun insertOrUpdateTodo(todo: Todo) {
        viewModelScope.launch { repository.insertTodo(todo) }
    }

    private fun sendUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch { _uiEventChannel.send(uiEvent) }
    }
}