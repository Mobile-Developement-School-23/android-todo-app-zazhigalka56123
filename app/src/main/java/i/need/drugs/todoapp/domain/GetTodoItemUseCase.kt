package i.need.drugs.todoapp.domain

class GetTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    fun getTodoItem(id: String): TodoItem = todoListRepository.getTodoItem(id)
}