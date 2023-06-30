package i.need.drugs.todoapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoItemDao {

    @Query("SELECT * FROM todoItems")
    fun getTodoList(): LiveData<List<TodoItemDbModel>>

    @Query("SELECT * FROM todoItems WHERE id=:todoItemId LIMIT 1")
    suspend fun getTodoItem(todoItemId: String): TodoItemDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodoItem(todoItem: TodoItemDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editTodoItem(todoItem: TodoItemDbModel)

    @Query("DELETE FROM todoItems WHERE id=:todoItemId")
    suspend fun deleteTodoItem(todoItemId: String)

    @Query("DELETE FROM todoItems")
    suspend fun clearTodoList()

}