package i.need.drugs.todoapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import i.need.drugs.todoapp.domain.TodoItem
import java.util.*

@Entity(tableName = "todoItems")
data class TodoItemDbModel (
    @PrimaryKey
    var id: String,
    var msg: String,
    var priority: Int,
    var deadline: Long?,
    var isCompleted: Boolean,
    val createDate: Long,
    var changedDate: Long? = null
)