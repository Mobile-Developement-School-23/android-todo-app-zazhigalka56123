package i.need.drugs.todoapp.domain.useCases

import i.need.drugs.todoapp.domain.TodoListRepository

class DeleteTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    suspend fun deleteTodoItem(id: String){
        todoListRepository.deleteTodoItem(id)
    }
}