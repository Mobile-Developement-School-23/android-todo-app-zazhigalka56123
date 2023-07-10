package i.need.drugs.todoapp.domain.db.useCases

import androidx.lifecycle.LiveData
import i.need.drugs.todoapp.domain.db.TodoItem
import i.need.drugs.todoapp.domain.db.TodoListRepository
import javax.inject.Inject

class GetTodoListUseCase @Inject constructor(private val todoListRepository: TodoListRepository) {

    fun getTodoList(): LiveData<List<TodoItem>> = todoListRepository.getTodoList()
}