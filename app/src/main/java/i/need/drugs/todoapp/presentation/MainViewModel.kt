package i.need.drugs.todoapp.presentation

import androidx.lifecycle.ViewModel
import i.need.drugs.todoapp.data.TodoListRepositoryImpl
import i.need.drugs.todoapp.domain.*

class MainViewModel : ViewModel() {

    private val repository = TodoListRepositoryImpl

    private val getTodoListUseCase = GetTodoListUseCase(repository)
    private val getTodoItemUseCase = GetTodoItemUseCase(repository)
    private val editTodoItemUseCase = EditTodoItemUseCase(repository)
    private val addTodoItemUseCase = AddTodoItemUseCase(repository)
    private val deleteTodoItemUseCase = DeleteTodoItemUseCase(repository)

    val todoList = getTodoListUseCase.getTodoList()


    fun getTodoItem(id: String) : TodoItem {
        return getTodoItemUseCase.getTodoItem(id)
    }

    fun editTodoItem(item: TodoItem) {
        editTodoItemUseCase.editTodoItem(item)
    }

    fun addTodoItem(item: TodoItem) {
        addTodoItemUseCase.addTodoItem(item)
    }

    fun changeDoneState(item: TodoItem){
        val newItem = item.copy(isCompleted = ! item.isCompleted)
        editTodoItemUseCase.editTodoItem(newItem)
    }

    fun deleteTodoItem(todoItem: TodoItem){
        deleteTodoItemUseCase.deleteTodoItem(todoItem)
    }


}