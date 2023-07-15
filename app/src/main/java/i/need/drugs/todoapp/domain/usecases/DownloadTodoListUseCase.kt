package i.need.drugs.todoapp.domain.usecases

import i.need.drugs.todoapp.domain.model.ResponseState
import i.need.drugs.todoapp.domain.model.ResponseState.State.STATE_ERROR
import i.need.drugs.todoapp.domain.repository.MainRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DownloadTodoListUseCase @Inject constructor(private val mainRepositoryImpl: MainRepository) {

    operator fun invoke() = flow {
        mainRepositoryImpl.downloadTodoList().collect {
            emit(it)
        }
    }.catch {
        emit(ResponseState(STATE_ERROR, null))
    }
}