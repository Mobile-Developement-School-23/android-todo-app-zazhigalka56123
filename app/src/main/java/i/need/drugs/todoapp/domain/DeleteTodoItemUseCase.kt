package i.need.drugs.todoapp.domain

class DeleteTodoItemUseCase(private val todoListRepository: TodoListRepository) {

    fun deleteTodoItem(item: TodoItem){
        todoListRepository.deleteTodoItem(item)
    }
}