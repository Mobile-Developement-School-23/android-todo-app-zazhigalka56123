package i.need.drugs.todoapp.domain.repository

import androidx.lifecycle.LiveData
import i.need.drugs.todoapp.domain.model.ResponseState
import i.need.drugs.todoapp.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface MainRepository {

    fun getTodoList(): LiveData<ResponseState<List<Todo>>>

    suspend fun downloadTodoList(): Flow<ResponseState<out List<Todo>?>>

    suspend fun getTodo(id: UUID): ResponseState<out Todo?>

    fun addTodo(item: Todo): Flow<ResponseState<out Nothing?>>

    fun editTodo(item: Todo): Flow<ResponseState<out Nothing?>>

    fun deleteTodo(item: Todo): Flow<ResponseState<out Nothing?>>

    fun updateTodoList(localList: List<Todo>): Flow<ResponseState<List<Todo>>>

}