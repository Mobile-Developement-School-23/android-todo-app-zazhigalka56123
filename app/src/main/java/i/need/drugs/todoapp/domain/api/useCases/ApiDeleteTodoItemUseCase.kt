package i.need.drugs.todoapp.domain.api.useCases

import i.need.drugs.todoapp.domain.api.ApiRepository
import i.need.drugs.todoapp.domain.db.TodoItem
import java.util.UUID
import javax.inject.Inject

class ApiDeleteTodoItemUseCase @Inject constructor(private val apiRepository: ApiRepository) {

    suspend fun deleteTodoItem(revision: Int, id: UUID): TodoItem? {
        return apiRepository.deleteTodoItem(revision, id)
    }
}