package i.need.drugs.todoapp.domain

class AddTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    fun addTodoItem(item: TodoItem) = todoListRepository.addTodoItem(item)

}