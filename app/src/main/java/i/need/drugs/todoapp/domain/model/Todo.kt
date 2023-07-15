package i.need.drugs.todoapp.domain.model

import java.util.*

data class Todo(
    val id: UUID,
    var msg: String,
    var priority: Priority,
    var deadline: Date?,
    var isCompleted: Boolean,
    val createDate: Date,
    var changedDate: Date?
)  {
    enum class Priority {
        URGENT,
        NORMAL,
        LOW
    }
}