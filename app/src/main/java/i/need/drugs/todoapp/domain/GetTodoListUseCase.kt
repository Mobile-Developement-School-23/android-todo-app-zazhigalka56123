package i.need.drugs.todoapp.domain

import androidx.lifecycle.LiveData

class GetTodoListUseCase(private val todoListRepository: TodoListRepository) {

    fun getTodoList(): LiveData<List<TodoItem>> = todoListRepository.getTodoList()
}