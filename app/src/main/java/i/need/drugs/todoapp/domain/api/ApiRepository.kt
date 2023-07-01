package i.need.drugs.todoapp.domain.api

import i.need.drugs.todoapp.domain.api.model.TodoItemResponseDto
import i.need.drugs.todoapp.domain.db.TodoItem
import retrofit2.Response
import java.util.*

interface ApiRepository {

    suspend fun downloadTodoList(): List<TodoItem>?

    suspend fun updateTodoList(revision: Int, body: List<TodoItem>): List<TodoItem>?

    suspend fun downloadTodoItem(id: String): TodoItem?

    suspend fun loadTodoItem(revision: Int, body: TodoItem): TodoItem?

    suspend fun editTodoItem(revision: Int, id: UUID, body: TodoItem): TodoItem?

    suspend fun deleteTodoItem(revision: Int, id: UUID): TodoItem?
}