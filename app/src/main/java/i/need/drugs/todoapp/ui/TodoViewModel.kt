package i.need.drugs.todoapp.ui

import androidx.lifecycle.ViewModel
import i.need.drugs.todoapp.domain.model.ResponseState
import i.need.drugs.todoapp.domain.model.Todo
import i.need.drugs.todoapp.domain.usecases.AddTodoUseCase
import i.need.drugs.todoapp.domain.usecases.DeleteTodoUseCase
import i.need.drugs.todoapp.domain.usecases.EditTodoUseCase
import i.need.drugs.todoapp.domain.usecases.GetTodoUseCase
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class TodoViewModel @Inject constructor(
    private val getTodoUseCase: GetTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val editTodoUseCase: EditTodoUseCase,

) : ViewModel() {

    suspend fun getTodo(id: UUID): ResponseState<out Todo?> {
        return getTodoUseCase(id)
    }

    fun deleteTodo(item: Todo) = flow {
        deleteTodoUseCase(item).collect {
            emit(it.state)
        }
    }

    fun addTodo(item: Todo) = flow {
        addTodoUseCase(item).collect {
            emit(it.state)
        }
    }

    fun editTodo(item: Todo) = flow {
        editTodoUseCase(item).collect {
            emit(it.state)
        }
    }
}