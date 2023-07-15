package i.need.drugs.todoapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_items")
data class TodoDbModel(
    @PrimaryKey
    var id: String,
    var msg: String,
    var priority: Int,
    var deadline: Long?,
    var isCompleted: Boolean,
    val createDate: Long,
    var changedDate: Long? = null
)