package i.need.drugs.todoapp.domain

class EditTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    fun editTodoItem(item: TodoItem) = todoListRepository.editTodoItem(item)
}