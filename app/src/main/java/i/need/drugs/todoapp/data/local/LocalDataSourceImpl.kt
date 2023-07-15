package i.need.drugs.todoapp.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import i.need.drugs.todoapp.domain.datasource.LocalDataSource
import i.need.drugs.todoapp.data.ResponseData
import i.need.drugs.todoapp.domain.model.Todo
import i.need.drugs.todoapp.domain.Constant.OK
import i.need.drugs.todoapp.domain.Constant.UNKNOWN_ERROR
import i.need.drugs.todoapp.domain.Constant.NOT_FOUND_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private var dao: TodoDao,
    private var mapper: LocalMapper
) : LocalDataSource {

    override fun getTodoList(): LiveData<ResponseData<List<Todo>>> =
        try {
            MediatorLiveData<ResponseData<List<Todo>>>().apply {
                addSource(dao.getTodoList()) {
                    value = ResponseData(OK, mapper.toListEntity(it))
                }
            }
        }catch (e: Exception){
            MutableLiveData(ResponseData(UNKNOWN_ERROR, null))
        }

    override suspend fun getTodo(id: UUID): ResponseData<out Todo?> {
        return try {
            val todoDbModel = dao.getTodo(id.toString())
            if (todoDbModel != null) {
                ResponseData(OK, mapper.toEntity(todoDbModel))
            } else {
                ResponseData(NOT_FOUND_ERROR, null)
            }
        }catch (e: Exception){
            ResponseData(UNKNOWN_ERROR, null)
        }
    }

    override suspend fun editTodo(item: Todo) {
        dao.editTodo(mapper.toDbModel(item))
    }

    override suspend fun addTodo(item: Todo) {
        dao.addTodo(mapper.toDbModel(item))
    }

    override suspend fun deleteTodo(item: Todo) {
        dao.deleteTodo(mapper.toDbModel(item))
    }

    override suspend fun deleteTodoList() {
        dao.deleteTodoList()
    }

    override suspend fun setTodoList(list: List<Todo>) {
        dao.deleteTodoList()
        dao.addTodoList(mapper.toListDbModel(list))
    }

}