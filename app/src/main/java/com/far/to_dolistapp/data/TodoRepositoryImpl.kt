package com.far.to_dolistapp.data

class TodoRepositoryImpl(private val dao: TodoDao) : TodoRepository {

    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }

    override suspend fun getTodoById(id: Int) = dao.getTodoById(id)

    override fun getTodos() = dao.getTodos()
}