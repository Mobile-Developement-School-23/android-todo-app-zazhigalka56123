package i.need.drugs.todoapp.domain.datasource

import androidx.lifecycle.LiveData
import i.need.drugs.todoapp.data.ResponseData
import i.need.drugs.todoapp.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalDataSource {

    fun getTodoList(): LiveData<ResponseData<List<Todo>>>

    suspend fun getTodo(id: UUID): ResponseData<out Todo?>

    suspend fun editTodo(item: Todo)

    suspend fun addTodo(item: Todo)

    suspend fun deleteTodo(item: Todo)

    suspend fun deleteTodoList()

    suspend fun setTodoList(list: List<Todo>)
}