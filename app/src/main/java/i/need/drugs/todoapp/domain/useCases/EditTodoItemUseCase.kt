package i.need.drugs.todoapp.domain.useCases

import i.need.drugs.todoapp.domain.TodoItem
import i.need.drugs.todoapp.domain.TodoListRepository

class EditTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    suspend fun editTodoItem(item: TodoItem) = todoListRepository.editTodoItem(item)
}