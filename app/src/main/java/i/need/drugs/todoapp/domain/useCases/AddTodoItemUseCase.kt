package i.need.drugs.todoapp.domain.useCases

import i.need.drugs.todoapp.domain.TodoItem
import i.need.drugs.todoapp.domain.TodoListRepository

class AddTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    suspend fun addTodoItem(item: TodoItem) = todoListRepository.addTodoItem(item)

}