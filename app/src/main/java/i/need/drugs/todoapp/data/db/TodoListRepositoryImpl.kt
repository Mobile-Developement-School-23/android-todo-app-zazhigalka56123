package i.need.drugs.todoapp.data.db

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import i.need.drugs.todoapp.domain.db.TodoItem
import i.need.drugs.todoapp.domain.db.TodoListRepository

class TodoListRepositoryImpl(application: Application) : TodoListRepository {

    private val todoItemDao = DatabaseApp.getInstance(application).todoItemDao()
    private val mapper = DatabaseMapper()


    override fun getTodoList(): LiveData<List<TodoItem>> =
        MediatorLiveData<List<TodoItem>>().apply {
            addSource(todoItemDao.getTodoList()) {
                value = mapper.mapTodoListDbModelToTodoListEntity(it)
            }
        }

    override suspend fun getTodoItem(id: String): TodoItem =
        mapper.mapDbModelToEntity(todoItemDao.getTodoItem(id))

    override suspend fun editTodoItem(item: TodoItem) {
        todoItemDao.editTodoItem(mapper.mapEntityToDbModel(item))
    }

    override suspend fun addTodoItem(item: TodoItem) =
        todoItemDao.addTodoItem(mapper.mapEntityToDbModel(item))

    override suspend fun deleteTodoItem(id: String) =
        todoItemDao.deleteTodoItem(id)

}