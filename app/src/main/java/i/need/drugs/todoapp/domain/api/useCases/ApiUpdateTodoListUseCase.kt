package i.need.drugs.todoapp.domain.api.useCases

import i.need.drugs.todoapp.domain.api.ApiRepository
import i.need.drugs.todoapp.domain.db.TodoItem
import javax.inject.Inject

class ApiUpdateTodoListUseCase @Inject constructor(private val apiRepository: ApiRepository) {

    suspend fun updateTodoList(revision: Int, list: List<TodoItem>): List<TodoItem>?{
        return apiRepository.updateTodoList(revision, list)
    }
}