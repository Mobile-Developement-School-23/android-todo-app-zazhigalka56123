package i.need.drugs.todoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import i.need.drugs.todoapp.domain.model.ResponseState
import i.need.drugs.todoapp.domain.model.Todo
import i.need.drugs.todoapp.domain.usecases.AddTodoUseCase
import i.need.drugs.todoapp.domain.usecases.DeleteTodoUseCase
import i.need.drugs.todoapp.domain.usecases.EditTodoUseCase
import i.need.drugs.todoapp.domain.usecases.GetTodoUseCase
import i.need.drugs.todoapp.ui.compose.TodoState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class TodoViewModel @Inject constructor(
    private val getTodoUseCase: GetTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val editTodoUseCase: EditTodoUseCase,

) : ViewModel() {
    private var isNewItem: Boolean = true
    
    var onNavigateBack: ( () -> Unit)? = null

    private val _todo = MutableStateFlow(
        Todo(
            id = UUID.randomUUID(),
            msg = "",
            priority = Todo.Priority.NORMAL,
            deadline = null,
            isCompleted = false,
            createDate = Calendar.getInstance().time,
            changedDate = null
        )
    )
    val todo = _todo.asStateFlow()

    fun observeState(state: TodoState) {
        when(state) {
            TodoState.AddTodo -> addTodo(todo.value)

            TodoState.Close -> onNavigateBack?.invoke()

            TodoState.DeleteTodo -> deleteTodo(todo.value)

            is TodoState.ChangedText -> _todo.update {
                todo.value.copy(
                    msg = state.text
                )
            }
            is TodoState.ChangedPriority -> _todo.update {
                todo.value.copy(
                    priority = state.priority
                )
            }
            is TodoState.ChangedDeadline -> _todo.update {
                todo.value.copy(
                    deadline = if (state.deadline != null) Date(state.deadline) else null
                )
            }
        }
    }

    suspend fun getTodo(id: UUID): ResponseState<out Todo?> {
        return getTodoUseCase(id)
    }

    fun deleteTodo(item: Todo) {
        if (!isNewItem) {
            viewModelScope.launch(Dispatchers.IO) {
                deleteTodoUseCase(item).collect {}
            }
        }
    }

    fun addTodo(item: Todo) {
        if (item.msg != "") {
            viewModelScope.launch(Dispatchers.IO) {
                addTodoUseCase(item).collect {
                }
            }
            onNavigateBack?.invoke()
        }
        
    }

    fun editTodo(item: Todo) = flow {
        editTodoUseCase(item).collect {
            emit(it.state)
        }
    }
}