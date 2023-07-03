package i.need.drugs.todoapp.presentation

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import i.need.drugs.todoapp.data.api.ApiRepositoryImpl
import i.need.drugs.todoapp.data.db.TodoListRepositoryImpl
import i.need.drugs.todoapp.domain.api.useCases.*
import i.need.drugs.todoapp.domain.db.TodoItem
import i.need.drugs.todoapp.domain.db.useCases.*
import kotlinx.coroutines.Dispatchers
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
    private val deleteTodoListUseCase = DeleteTodoListUseCase(repository)

    private val apiLoadTodoItemUseCase = ApiLoadTodoItemUseCase(apiRepository)
    private val apiDownloadTodoListUseCase = ApiDownloadTodoListUseCase(apiRepository)
    private val apiEditTodoItemUseCase = ApiEditTodoItemUseCase(apiRepository)
    private val apiDeleteTodoItemUseCase = ApiDeleteTodoItemUseCase(apiRepository)
    private val apiUpdateTodoListUseCase = ApiUpdateTodoListUseCase(apiRepository)

    var todoList = getTodoListUseCase.getTodoList()
    var todoItem = MutableLiveData<TodoItem>()


    fun downloadTodoList(activity: Activity){
        viewModelScope.launch(Dispatchers.IO) {
            val request = apiDownloadTodoListUseCase.downloadTodoList()
            Log.d("downloadTodoList", request.toString())
            if(request == null) {
                activity.snackBar("Не удалось обновить данные")
                activity.setNeedUpdate(true)
            }else {
                deleteTodoListUseCase.deleteTodoList()
                request.forEach {
                    addTodoItemUseCase.addTodoItem(it)
                }
            }
        }
    }

    fun updateTodoList(activity: Activity, revision: Int, list: List<TodoItem>){
        viewModelScope.launch(Dispatchers.IO) {
            val request = apiUpdateTodoListUseCase.updateTodoList(revision, list)
            Log.d("updateTodoList", request?.size.toString())
            if (request == null) {
                activity.snackBar("Не удалось синхронизироваться с сервером")
                activity.setNeedUpdate(true)
            } else {
                deleteTodoListUseCase.deleteTodoList()
                request.forEach {
                    addTodoItemUseCase.addTodoItem(it)
                }
            }
        }
    }

    fun getTodoItem(id: String)  {
        viewModelScope.launch(Dispatchers.IO) {
            todoItem.postValue(getTodoItemUseCase.getTodoItem(id))
        }

    }

    fun editTodoItem(activity: Activity, revision: Int, id: UUID, item: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = apiEditTodoItemUseCase.editTodoItem(revision, id, item)
            Log.d("editTodoItem", request.toString())
            if (request == null){
                activity.snackBar("Не удалось изменить данные на сервере")
                activity.setNeedUpdate(true)
            }
            editTodoItemUseCase.editTodoItem(item)
        }
    }

    fun addTodoItem(activity: Activity, revision: Int, item: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = apiLoadTodoItemUseCase.loadTodoItem(revision, item)
            Log.d("addTodoItem", request.toString())
            if (request == null){
               activity.snackBar("Не удалось добавить данные на сервер")
                activity.setNeedUpdate(true)
            }
            addTodoItemUseCase.addTodoItem(item)
        }
    }

    fun changeDoneState(activity: Activity, revision: Int, item: TodoItem){
        viewModelScope.launch(Dispatchers.IO) {
            val newItem = item.copy(isCompleted = ! item.isCompleted)
            val request = apiEditTodoItemUseCase.editTodoItem(revision, UUID.fromString(item.id), newItem)
            Log.d("changeDoneState", request.toString())
            if (request == null){
                Log.d("fsdfsd", "dasdas")
                activity.snackBar("Не удалось изменить состояние задачи на сервере")
                activity.setNeedUpdate(true)
            }
            editTodoItemUseCase.editTodoItem(newItem)
        }
    }

    fun deleteTodoItem(activity: Activity, revision: Int, id: String){
        viewModelScope.launch(Dispatchers.IO) {
            val request = apiDeleteTodoItemUseCase.deleteTodoItem(revision, UUID.fromString(id))
            Log.d("deleteTodoItem", request.toString())
            if (request == null){
                activity.snackBar("Не удалось удалить элемент на сервере")
                activity.setNeedUpdate(true)
            }
            deleteTodoItemUseCase.deleteTodoItem(id)
        }
    }


}