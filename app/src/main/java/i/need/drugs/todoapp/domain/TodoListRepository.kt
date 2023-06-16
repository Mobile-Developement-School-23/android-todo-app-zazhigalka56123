package i.need.drugs.todoapp.domain

import androidx.lifecycle.LiveData

interface TodoListRepository {

    fun getTodoList(): LiveData<List<TodoItem>>

    fun getTodoItem(id: String): TodoItem

    fun editTodoItem(item: TodoItem)

    fun addTodoItem(item: TodoItem)

    fun deleteTodoItem(item: TodoItem)


}