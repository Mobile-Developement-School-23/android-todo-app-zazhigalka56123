package i.need.drugs.todoapp.domain.datasource

import i.need.drugs.todoapp.data.ResponseData
import i.need.drugs.todoapp.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface RemoteDataSource {

    fun downloadTodoList(): Flow<ResponseData<out List<Todo>?>>

    suspend fun updateTodoList(list: List<Todo>): Flow<ResponseData<out List<Todo>?>>

    suspend fun addTodo(todo: Todo): Flow<ResponseData<out Nothing>>

    suspend fun editTodo(id: UUID, todo: Todo): Flow<ResponseData<out Nothing>>

    suspend fun deleteTodo(todo: Todo): Flow<ResponseData<out Nothing>>
}