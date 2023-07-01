package i.need.drugs.todoapp.domain.db.useCases

import i.need.drugs.todoapp.domain.db.TodoItem
import i.need.drugs.todoapp.domain.db.TodoListRepository

class EditTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    suspend fun editTodoItem(item: TodoItem) = todoListRepository.editTodoItem(item)
}