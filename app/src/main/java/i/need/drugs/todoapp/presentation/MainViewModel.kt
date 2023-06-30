package i.need.drugs.todoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import i.need.drugs.todoapp.data.db.TodoListRepositoryImpl
import i.need.drugs.todoapp.domain.*
import i.need.drugs.todoapp.domain.useCases.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TodoListRepositoryImpl(application)

    private val getTodoListUseCase = GetTodoListUseCase(repository)
    private val getTodoItemUseCase = GetTodoItemUseCase(repository)
    private val editTodoItemUseCase = EditTodoItemUseCase(repository)
    private val addTodoItemUseCase = AddTodoItemUseCase(repository)
    private val deleteTodoItemUseCase = DeleteTodoItemUseCase(repository)

    val todoList = getTodoListUseCase.getTodoList()


    suspend fun getTodoItem(id: String) : TodoItem {
        return getTodoItemUseCase.getTodoItem(id)
    }

    suspend fun editTodoItem(item: TodoItem) {
        editTodoItemUseCase.editTodoItem(item)
    }

    suspend fun addTodoItem(item: TodoItem) {
        addTodoItemUseCase.addTodoItem(item)
    }

    suspend fun changeDoneState(item: TodoItem){
        val newItem = item.copy(isCompleted = ! item.isCompleted)
        editTodoItemUseCase.editTodoItem(newItem)
    }

    suspend fun deleteTodoItem(id: String){
        deleteTodoItemUseCase.deleteTodoItem(id)
    }


}