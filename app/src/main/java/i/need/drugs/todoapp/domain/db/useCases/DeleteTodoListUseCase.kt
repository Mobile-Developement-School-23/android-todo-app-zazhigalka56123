package i.need.drugs.todoapp.domain.db.useCases

import i.need.drugs.todoapp.domain.db.TodoListRepository
import javax.inject.Inject

class DeleteTodoListUseCase @Inject constructor(private val todoListRepository: TodoListRepository) {

    suspend fun deleteTodoList(){
        todoListRepository.deleteTodoList()
    }
}