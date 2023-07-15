package i.need.drugs.todoapp.ui

import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import i.need.drugs.todoapp.data.util.NetCallback
import i.need.drugs.todoapp.domain.model.ResponseState
import i.need.drugs.todoapp.domain.model.Todo
import i.need.drugs.todoapp.domain.usecases.AddTodoUseCase
import i.need.drugs.todoapp.domain.usecases.DeleteTodoUseCase
import i.need.drugs.todoapp.domain.usecases.DownloadTodoListUseCase
import i.need.drugs.todoapp.domain.usecases.EditTodoUseCase
import i.need.drugs.todoapp.domain.usecases.GetTodoListUseCase
import i.need.drugs.todoapp.domain.usecases.UpdateTodoListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getTodoListUseCase: GetTodoListUseCase,
    private val downloadTodoListUseCase: DownloadTodoListUseCase,
    private val editTodoUseCase: EditTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val updateTodoListUseCase: UpdateTodoListUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    val connectManager: ConnectivityManager
    ) : ViewModel() {

    val todoList = getTodoListUseCase()

    init {
        val netCallback = NetCallback(this)
        connectManager.registerDefaultNetworkCallback(netCallback)
        netCallback.onNetworkAvailable = {
            viewModelScope.launch(Dispatchers.IO) {
                todoList.value?.data?.let { updateTodoList(it) }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            downloadTodoListUseCase().collect { response ->
                if (response.state == ResponseState.State.STATE_OK && response.data != null) {
                    todoList.value?.data?.let { updateTodoList(it) }
//                    _todoList.postValue(response.data!!)
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

    suspend fun updateTodoList(list: List<Todo>) {
        updateTodoListUseCase(list).collect { response ->
        }
    }



    fun changeDoneState(item: Todo) = flow {
        val newItem = item.copy(isCompleted = ! item.isCompleted)
        editTodoUseCase(newItem).collect {
            emit(it.state)
        }
    }

}