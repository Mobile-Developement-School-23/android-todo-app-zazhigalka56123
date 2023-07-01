package i.need.drugs.todoapp.domain.api.useCases

import android.util.Log
import i.need.drugs.todoapp.domain.api.ApiRepository
import i.need.drugs.todoapp.domain.db.TodoItem
import java.util.*

class ApiEditTodoItemUseCase(private val apiRepository: ApiRepository) {

    suspend fun editTodoItem(revision: Int, id: UUID, item: TodoItem): TodoItem? {
        return apiRepository.editTodoItem(revision, id, item)
    }
}