package i.need.drugs.todoapp.domain.api.useCases

import i.need.drugs.todoapp.domain.api.ApiRepository
import i.need.drugs.todoapp.domain.db.TodoItem

class ApiLoadTodoItemUseCase(private val apiRepository: ApiRepository) {

    suspend fun loadTodoItem(revision: Int, todoItem: TodoItem): TodoItem? {
        return apiRepository.loadTodoItem(revision, todoItem)
    }
}