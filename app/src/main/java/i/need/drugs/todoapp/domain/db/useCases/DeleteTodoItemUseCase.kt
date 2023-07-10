package i.need.drugs.todoapp.domain.db.useCases

import i.need.drugs.todoapp.domain.db.TodoListRepository
import javax.inject.Inject

class DeleteTodoItemUseCase @Inject constructor(private val todoListRepository: TodoListRepository) {

    suspend fun deleteTodoItem(id: String){
        todoListRepository.deleteTodoItem(id)
    }
}