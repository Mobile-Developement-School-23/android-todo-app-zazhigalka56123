package i.need.drugs.todoapp.domain.db

import androidx.lifecycle.LiveData

interface TodoListRepository {

    fun getTodoList(): LiveData<List<TodoItem>>

    suspend fun getTodoItem(id: String): TodoItem

    suspend fun editTodoItem(item: TodoItem)

    suspend fun addTodoItem(item: TodoItem)

    suspend fun deleteTodoItem(id: String)

}