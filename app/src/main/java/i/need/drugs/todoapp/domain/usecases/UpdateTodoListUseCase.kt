package i.need.drugs.todoapp.domain.usecases

import android.util.Log
import i.need.drugs.todoapp.domain.model.ResponseState
import i.need.drugs.todoapp.domain.model.ResponseState.State.STATE_ERROR
import i.need.drugs.todoapp.domain.model.Todo
import i.need.drugs.todoapp.domain.repository.MainRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateTodoListUseCase @Inject constructor(private val mainRepositoryImpl: MainRepository) {

    operator fun invoke(list: List<Todo>) = flow {
        mainRepositoryImpl.updateTodoList(list).collect {
            Log.d("TttUUs", it.state.toString())
            emit(it)
        }
    }.catch {
        Log.d("TttUUs", it.message.toString())
        emit(ResponseState(STATE_ERROR, null))
    }
}