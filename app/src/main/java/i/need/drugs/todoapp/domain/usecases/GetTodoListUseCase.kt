package i.need.drugs.todoapp.domain.usecases

import androidx.lifecycle.LiveData
import i.need.drugs.todoapp.domain.model.ResponseState
import i.need.drugs.todoapp.domain.model.Todo
import i.need.drugs.todoapp.domain.repository.MainRepository
import javax.inject.Inject

class GetTodoListUseCase @Inject constructor(private val mainRepositoryImpl: MainRepository) {

    operator fun invoke(): LiveData<ResponseState<List<Todo>>> {
        return mainRepositoryImpl.getTodoList()
    }
}