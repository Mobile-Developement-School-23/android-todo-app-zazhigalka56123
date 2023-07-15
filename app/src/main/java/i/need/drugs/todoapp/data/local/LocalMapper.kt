package i.need.drugs.todoapp.data.local

import i.need.drugs.todoapp.domain.model.Todo
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class LocalMapper @Inject constructor() {

    fun toDbModel(todo: Todo) = TodoDbModel(
        id = todo.id.toString(),
        msg = todo.msg,
        priority = mapPriorityToInt(todo.priority),
        deadline = mapDateToLong(todo.deadline),
        isCompleted = todo.isCompleted,
        createDate = mapDateToLong(todo.createDate)!!,
        changedDate = mapDateToLong(todo.changedDate)
    )

    fun toEntity(todoDbModel: TodoDbModel) = Todo(
        id = UUID.fromString(todoDbModel.id),
        msg = todoDbModel.msg,
        priority = mapIntToPriority(todoDbModel.priority),
        deadline = mapLongToDate(todoDbModel.deadline),
        isCompleted = todoDbModel.isCompleted,
        createDate = mapLongToDate(todoDbModel.createDate)!!,
        changedDate = mapLongToDate(todoDbModel.changedDate)
    )

    fun toListEntity(list: List<TodoDbModel>) = list.map {
        toEntity(it)
    }
    fun toListDbModel(list: List<Todo>) = list.map {
        toDbModel(it)
    }
    private fun mapPriorityToInt(priority: Todo.Priority) =
        when (priority){
            Todo.Priority.LOW -> 0
            Todo.Priority.NORMAL -> 1
            Todo.Priority.URGENT -> 2
        }
    private fun mapIntToPriority(priority: Int) =
        when (priority){
            0 -> Todo.Priority.LOW
            1 -> Todo.Priority.NORMAL
            2 -> Todo.Priority.URGENT
            else -> Todo.Priority.NORMAL
        }

    private fun mapDateToLong(date: Date?) = date?.time

    private fun mapLongToDate(time: Long?): Date? {
        return if (time == null)
            null
        else {
            val date = Date()
            date.time = time
            date
        }
    }
}