package com.far.to_dolistapp.ui.add_edit

sealed class AddEditTodoEvent {

    data class OnTitleChange(val title: String): AddEditTodoEvent()

    data class OnDescriptionChange(val description: String): AddEditTodoEvent()

    object OnSaveTodoClick: AddEditTodoEvent()
}
