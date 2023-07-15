package i.need.drugs.todoapp.data.remote

import android.content.SharedPreferences
import android.util.Log
import i.need.drugs.todoapp.TodoApp.Companion.setRevision
import i.need.drugs.todoapp.data.remote.model.TodoResponse
import i.need.drugs.todoapp.domain.Constant.OK
import i.need.drugs.todoapp.domain.Constant.REVISION_ERROR
import i.need.drugs.todoapp.domain.Constant.UNKNOWN_ERROR
import i.need.drugs.todoapp.domain.datasource.RemoteDataSource
import i.need.drugs.todoapp.data.ResponseData
import i.need.drugs.todoapp.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: TodoApi,
    private val mapper: RemoteMapper,
    private val sp: SharedPreferences
) : RemoteDataSource {
    override fun downloadTodoList(): Flow<ResponseData<out List<Todo>?>> = flow {
        val response = api.downloadTodoList()
        val revision = response.body()?.revision
        if (revision != null) {
            setRevision(revision, sp)
        }
        Log.d("ttttt", response.body().toString())
        Log.d("ttttt", codeToConst(response.code()).toString())
        Log.d("ttttt", mapper.toListEntity(response.body()?.list).toString())
        emit(ResponseData(codeToConst(response.code()), mapper.toListEntity(response.body()?.list)))
    }.catch {
        emit(ResponseData(UNKNOWN_ERROR, null))
    }

    override suspend fun updateTodoList(list: List<Todo>): Flow<ResponseData<out List<Todo>?>> = flow {
        val response = api.updateTodoList(mapper.toListRequestDto(list))
        val revision = response.body()?.revision
        if (revision != null) {
            setRevision(revision, sp)
        }
        emit(ResponseData(codeToConst(response.code()), mapper.toListEntity(response.body()?.list)))
    }.catch {
        emit(ResponseData(UNKNOWN_ERROR, null))
    }


    override suspend fun addTodo(todo: Todo): Flow<ResponseData<out Nothing>> = flow {
        Log.d("addTodo_q", "start")
        val response = api.addTodo(mapper.toRequestDto(todo))
        Log.d("addTodo_q", "end")
        Log.d("addTodo_q", response.raw().toString())
        emit(checkTodoResponse(response))
    }.catch {
        emit(ResponseData(UNKNOWN_ERROR, null))
    }

    override suspend fun editTodo(id: UUID, todo: Todo): Flow<ResponseData<out Nothing>> = flow {
        val response = api.editTodo(id, mapper.toRequestDto(todo))

        emit(checkTodoResponse(response))
    }.catch {
        emit(ResponseData(UNKNOWN_ERROR, null))
    }

    override suspend fun deleteTodo(todo: Todo): Flow<ResponseData<out Nothing>> = flow {
        val response = api.deleteTodo(todo.id)

        emit(checkTodoResponse(response))
    }.catch {
        emit(ResponseData(UNKNOWN_ERROR, null))
    }

    private fun checkTodoResponse(response: retrofit2.Response<TodoResponse>): ResponseData<Nothing> {
        val revision = response.body()?.revision
        if (revision != null) {
            setRevision(revision, sp)
        }
        return ResponseData(codeToConst(response.code()), null)
    }

    private fun codeToConst(code: Int) =
        when(code){
            OK -> OK
            REVISION_ERROR -> REVISION_ERROR
            else -> UNKNOWN_ERROR
        }
}

