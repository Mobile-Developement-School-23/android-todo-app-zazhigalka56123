package i.need.drugs.todoapp.domain.api.useCases

import i.need.drugs.todoapp.domain.api.ApiRepository
import i.need.drugs.todoapp.domain.db.TodoItem
import java.util.*

class ApiDownloadTodoListUseCase(private val apiRepository: ApiRepository) {

    suspend fun downloadTodoList(): List<TodoItem>? {
        return apiRepository.downloadTodoList()
    }
}