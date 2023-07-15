package i.need.drugs.todoapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_items")
    fun getTodoList(): LiveData<List<TodoDbModel>>

    @Query("SELECT * FROM todo_items WHERE id=:id LIMIT 1")
    suspend fun getTodo(id: String): TodoDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todo: TodoDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodoList(list: List<TodoDbModel>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editTodo(todo: TodoDbModel)

    @Delete(entity = TodoDbModel::class)
    suspend fun deleteTodo(todo: TodoDbModel)

    @Query("DELETE FROM todo_items")
    suspend fun deleteTodoList()
}