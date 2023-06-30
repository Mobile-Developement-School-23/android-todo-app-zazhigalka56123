package i.need.drugs.todoapp.domain.useCases

import androidx.lifecycle.LiveData
import i.need.drugs.todoapp.domain.TodoItem
import i.need.drugs.todoapp.domain.TodoListRepository

class GetTodoListUseCase(private val todoListRepository: TodoListRepository) {

    fun getTodoList(): LiveData<List<TodoItem>> = todoListRepository.getTodoList()
}