package i.need.drugs.todoapp.domain.db.useCases

import i.need.drugs.todoapp.domain.db.TodoItem
import i.need.drugs.todoapp.domain.db.TodoListRepository

class AddTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    suspend fun addTodoItem(item: TodoItem) = todoListRepository.addTodoItem(item)

}