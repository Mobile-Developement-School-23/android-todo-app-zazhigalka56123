package i.need.drugs.todoapp.data

import androidx.lifecycle.MediatorLiveData
import i.need.drugs.todoapp.data.util.Merger
import i.need.drugs.todoapp.domain.Constant.NOT_FOUND_ERROR
import i.need.drugs.todoapp.domain.Constant.OK
import i.need.drugs.todoapp.domain.Constant.REVISION_ERROR
import i.need.drugs.todoapp.domain.datasource.LocalDataSource
import i.need.drugs.todoapp.domain.datasource.RemoteDataSource
import i.need.drugs.todoapp.domain.model.ResponseState
import i.need.drugs.todoapp.domain.model.ResponseState.State.STATE_ERROR
import i.need.drugs.todoapp.domain.model.ResponseState.State.STATE_NOT_FOUND
import i.need.drugs.todoapp.domain.model.ResponseState.State.STATE_OFFLINE
import i.need.drugs.todoapp.domain.model.ResponseState.State.STATE_OK
import i.need.drugs.todoapp.domain.model.Todo
import i.need.drugs.todoapp.domain.notification.NotificationRepository
import i.need.drugs.todoapp.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val localDS: LocalDataSource,
    private val remoteDS: RemoteDataSource,
    private val merger: Merger,
    private val notificationRepository: NotificationRepository
) : MainRepository {
    override fun getTodoList() =
        MediatorLiveData<ResponseState<List<Todo>>>().apply {
            addSource(localDS.getTodoList()) {
                value = when(it.code){
                    OK -> ResponseState(STATE_OK, it.todo)
                    else -> ResponseState(STATE_ERROR, it.todo)
                }
            }
        }


    override suspend fun downloadTodoList(): Flow<ResponseState<out List<Todo>?>> = flow {
        remoteDS.downloadTodoList().collect { response ->
            when (response.code) {
                OK -> emit(ResponseState(STATE_OK, response.todo))

                REVISION_ERROR -> emit(ResponseState(STATE_ERROR, response.todo))

                else -> emit(ResponseState(STATE_OFFLINE, localDS.getTodoList().value?.todo))
            }
        }
    }.catch { emit(ResponseState(STATE_ERROR, null)) }

    override suspend fun getTodo(id: UUID): ResponseState<out Todo?> {
        return try {
            val response = localDS.getTodo(id)
            when (response.code) {
                OK -> ResponseState(STATE_OK, response.todo)

                NOT_FOUND_ERROR -> ResponseState(STATE_NOT_FOUND, response.todo)

                else -> ResponseState(STATE_ERROR, null)
            }
        } catch (e: Exception) {
            ResponseState(STATE_ERROR, null)
        }
    }

    override fun addTodo(item: Todo) = flow {
        localDS.addTodo(item)
        notificationRepository.setNotification(item)
        remoteDS.addTodo(item).collect { response ->
            when (response.code) {
                OK -> emit(ResponseState(STATE_OK, null))

                else -> emit(ResponseState(STATE_ERROR, response.todo))
            }
        }
    }.catch { emit(ResponseState(STATE_ERROR, null)) }


    override fun editTodo(item: Todo) = flow {
        localDS.editTodo(item)
        notificationRepository.setNotification(item)
        remoteDS.editTodo(item.id, item).collect { response ->
            when (response.code) {
                OK -> emit(ResponseState(STATE_OK, null))

                else -> emit(ResponseState(STATE_ERROR, response.todo))
            }
        }
    }.catch { emit(ResponseState(STATE_ERROR, null)) }

    override fun deleteTodo(item: Todo) = flow {
        localDS.deleteTodo(item)
        notificationRepository.deleteNotification(item)
        remoteDS.deleteTodo(item).collect { response ->
            when (response.code) {
                OK -> emit(ResponseState(STATE_OK, null))

                else -> emit(ResponseState(STATE_ERROR, response.todo))

            }
        }
    }.catch { emit(ResponseState(STATE_ERROR, null)) }

    override fun updateTodoList(localList: List<Todo>) = flow {
        var response = ResponseState(STATE_ERROR, emptyList<Todo>())
        var remoteList: List<Todo> = emptyList()

        
        remoteDS.downloadTodoList().collect { remoteResponse ->
            if (remoteResponse.code == OK && remoteResponse.todo != null) {
                remoteList = remoteResponse.todo
            } else {
                response = ResponseState(STATE_ERROR, null)
            }
        }
        val list = merger.merge(localList, remoteList)

        remoteDS.updateTodoList(list).collect {
            response = if (it.code == OK && it.todo != null) {
                localDS.setTodoList(it.todo)
                ResponseState(STATE_OK, it.todo)
            } else {
                ResponseState(STATE_ERROR, null)
            }
        }
        if (response.state == STATE_OK){
            localList.forEach { notificationRepository.deleteNotification(it) }
            response.data?.forEach { notificationRepository.setNotification(it) }
        }
        emit(response)
    }.catch {
        emit(ResponseState(STATE_ERROR, null))
    }


}