package i.need.drugs.todoapp.domain.api.useCases

import android.util.Log
import i.need.drugs.todoapp.domain.api.ApiRepository
import i.need.drugs.todoapp.domain.db.TodoItem
import java.util.*
import javax.inject.Inject

class ApiEditTodoItemUseCase @Inject constructor(private val apiRepository: ApiRepository) {

    suspend fun editTodoItem(revision: Int, id: UUID, item: TodoItem): TodoItem? {
        return apiRepository.editTodoItem(revision, id, item)
    }
}