package i.need.drugs.todoapp.domain.api.useCases

import i.need.drugs.todoapp.domain.api.ApiRepository
import i.need.drugs.todoapp.domain.db.TodoItem

class ApiUpdateTodoListUseCase(private val apiRepository: ApiRepository) {

    suspend fun updateTodoList(revision: Int, list: List<TodoItem>): List<TodoItem>?{
        return apiRepository.updateTodoList(revision, list)
    }
}