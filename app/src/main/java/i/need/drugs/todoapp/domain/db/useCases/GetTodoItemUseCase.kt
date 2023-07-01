package i.need.drugs.todoapp.domain.db.useCases

import i.need.drugs.todoapp.domain.db.TodoItem
import i.need.drugs.todoapp.domain.db.TodoListRepository

class GetTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    suspend fun getTodoItem(id: String): TodoItem = todoListRepository.getTodoItem(id)
}