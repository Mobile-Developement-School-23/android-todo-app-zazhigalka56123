package i.need.drugs.todoapp.domain.db.useCases

import i.need.drugs.todoapp.domain.db.TodoListRepository

class DeleteTodoListUseCase(private val todoListRepository: TodoListRepository) {

    suspend fun deleteTodoList(){
        todoListRepository.deleteTodoList()
    }
}