package i.need.drugs.todoapp.domain.db.useCases

import i.need.drugs.todoapp.domain.db.TodoItem
import i.need.drugs.todoapp.domain.db.TodoListRepository
import javax.inject.Inject

class AddTodoItemUseCase @Inject constructor(private val todoListRepository: TodoListRepository) {

    suspend fun addTodoItem(item: TodoItem) = todoListRepository.addTodoItem(item)

}