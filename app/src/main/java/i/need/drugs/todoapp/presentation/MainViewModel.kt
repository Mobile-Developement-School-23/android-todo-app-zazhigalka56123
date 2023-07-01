package i.need.drugs.todoapp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import i.need.drugs.todoapp.data.api.ApiRepositoryImpl
import i.need.drugs.todoapp.data.db.TodoListRepositoryImpl
import i.need.drugs.todoapp.domain.api.useCases.*
import i.need.drugs.todoapp.domain.db.TodoItem
import i.need.drugs.todoapp.domain.db.useCases.*
import kotlinx.coroutines.launch
import java.util.UUID

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TodoListRepositoryImpl(application)
    private val apiRepository = ApiRepositoryImpl(application)

    private val getTodoListUseCase = GetTodoListUseCase(repository)
    private val getTodoItemUseCase = GetTodoItemUseCase(repository)
    private val editTodoItemUseCase = EditTodoItemUseCase(repository)
    private val addTodoItemUseCase = AddTodoItemUseCase(repository)
    private val deleteTodoItemUseCase = DeleteTodoItemUseCase(repository)

    private val apiLoadTodoItemUseCase = ApiLoadTodoItemUseCase(apiRepository)
    private val apiDownloadTodoListUseCase = ApiDownloadTodoListUseCase(apiRepository)
    private val apiEditTodoItemUseCase = ApiEditTodoItemUseCase(apiRepository)
    private val apiDeleteTodoItemUseCase = ApiDeleteTodoItemUseCase(apiRepository)
    private val apiUpdateTodoListUseCase = ApiUpdateTodoListUseCase(apiRepository)

    var todoList = getTodoListUseCase.getTodoList()
    var todoItem = MutableLiveData<TodoItem>()

    var msgSnackbar = MutableLiveData<String>(null)

    fun downloadTodoList(){
        viewModelScope.launch {
            val request = apiDownloadTodoListUseCase.downloadTodoList()
            Log.d("downloadTodoList", request.toString())
            if(request == null) {
                msgSnackbar.postValue("Не удалось обновить данные")
            }else{
                request.forEach {
                    addTodoItemUseCase.addTodoItem(it)
                }
            }
        }
    }

    fun updateTodoList(revision: Int, list: List<TodoItem>){
        viewModelScope.launch {
            val request = apiUpdateTodoListUseCase.updateTodoList(revision, list)
            Log.d("updateTodoList", request.toString())
            if(request == null) {
                msgSnackbar.postValue("Не удалось синхронизироваться с сервером")
            }else{
                request.forEach {
                    addTodoItemUseCase.addTodoItem(it)
                }
            }
        }
    }

    fun getTodoItem(id: String)  {
        viewModelScope.launch {
            todoItem.postValue(getTodoItemUseCase.getTodoItem(id))
        }

    }

    fun editTodoItem(revision: Int, id: UUID, item: TodoItem) {
        viewModelScope.launch {
            val request = apiEditTodoItemUseCase.editTodoItem(revision, id, item)
            Log.d("editTodoItem", request.toString())
            if (request == null){
                msgSnackbar.postValue("Не удалось синхронизировать данные с сервером")
            }
            editTodoItemUseCase.editTodoItem(item)
        }
    }

    fun addTodoItem(revision: Int, item: TodoItem) {
        viewModelScope.launch {
            val request = apiLoadTodoItemUseCase.loadTodoItem(revision, item)
            Log.d("addTodoItem", request.toString())
            if (request == null){
               msgSnackbar.postValue("Не удалось синхронизировать данные с сервером")
            }
            addTodoItemUseCase.addTodoItem(item)
        }
    }

    fun changeDoneState(revision: Int, item: TodoItem){
        viewModelScope.launch {
            val newItem = item.copy(isCompleted = ! item.isCompleted)
            val request = apiEditTodoItemUseCase.editTodoItem(revision, UUID.fromString(item.id), newItem)
            Log.d("changeDoneState", request.toString())
            if (request == null){
                Log.d("fsdfsd", "dasdas")
                msgSnackbar.postValue("Не удалось синхронизировать данные с сервером")
            }
            editTodoItemUseCase.editTodoItem(newItem)
        }
    }

    fun deleteTodoItem(revision: Int, id: String){
        viewModelScope.launch {
            val request = apiDeleteTodoItemUseCase.deleteTodoItem(revision, UUID.fromString(id))
            if (request == null){
                msgSnackbar.postValue("Не удалось синхронизировать данные с сервером")
            }
            deleteTodoItemUseCase.deleteTodoItem(id)
        }
    }


}