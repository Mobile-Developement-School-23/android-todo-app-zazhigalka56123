package i.need.drugs.todoapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import i.need.drugs.todoapp.domain.model.ResponseState
import i.need.drugs.todoapp.domain.model.Todo
import i.need.drugs.todoapp.domain.usecases.AddTodoUseCase
import i.need.drugs.todoapp.domain.usecases.DeleteTodoUseCase
import i.need.drugs.todoapp.domain.usecases.DownloadTodoListUseCase
import i.need.drugs.todoapp.domain.usecases.EditTodoUseCase
import i.need.drugs.todoapp.domain.usecases.GetTodoListUseCase
import i.need.drugs.todoapp.domain.usecases.UpdateTodoListUseCase
import i.need.drugs.todoapp.ui.MainActivity.Companion.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getTodoListUseCase: GetTodoListUseCase,
    private val downloadTodoListUseCase: DownloadTodoListUseCase,
    private val editTodoUseCase: EditTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val updateTodoListUseCase: UpdateTodoListUseCase,
    private val addTodoUseCase: AddTodoUseCase
    ) : ViewModel() {

    val todoList = getTodoListUseCase()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            downloadTodoListUseCase().collect { response ->
                if (response.state == ResponseState.State.STATE_OK && response.data != null) {
                    todoList.value?.data?.let {
                        updateTodoList(it).collect {}
                    }
                    if (isOnline.value != true) isOnline.postValue(true)
                }else{
                    if (isOnline.value != false) isOnline.postValue(false)
                }
            }
        }
    }

    fun deleteTodo(item: Todo) = flow {
        deleteTodoUseCase.invoke(item).collect {
            emit(it.state)
        }
    }

    fun addTodo(todo: Todo) = flow {
        addTodoUseCase(todo).collect{ response ->
            emit(response.state)
        }
    }

    suspend fun updateTodoList(list: List<Todo>) = flow {
        updateTodoListUseCase(list).collect { response ->
            emit(response.state)
        }
    }



    fun changeDoneState(item: Todo) = flow {
        val newItem = item.copy(isCompleted = ! item.isCompleted)
        editTodoUseCase(newItem).collect {
            emit(it.state)
        }
    }

}