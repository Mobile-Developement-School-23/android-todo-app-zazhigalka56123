package i.need.drugs.todoapp.domain.db.useCases

import i.need.drugs.todoapp.domain.db.TodoListRepository

class DeleteTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    suspend fun deleteTodoItem(id: String){
        todoListRepository.deleteTodoItem(id)
    }
}