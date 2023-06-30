package i.need.drugs.todoapp.domain.useCases

import i.need.drugs.todoapp.domain.TodoItem
import i.need.drugs.todoapp.domain.TodoListRepository

class GetTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    suspend fun getTodoItem(id: String): TodoItem = todoListRepository.getTodoItem(id)
}