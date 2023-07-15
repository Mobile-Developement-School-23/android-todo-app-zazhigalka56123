package i.need.drugs.todoapp.data.remote

import android.os.Build
import i.need.drugs.todoapp.data.remote.model.TodoDto
import i.need.drugs.todoapp.data.remote.model.TodoListRequest
import i.need.drugs.todoapp.data.remote.model.TodoRequest
import i.need.drugs.todoapp.domain.model.Todo
import java.util.Date
import javax.inject.Inject

class RemoteMapper @Inject constructor() {

    private fun toEntity(todoItem: TodoDto) = Todo(
        id = todoItem.id,
        msg = todoItem.msg,
        priority = mapStringToPriority(todoItem.priority),
        deadline = mapLongToDate(todoItem.deadline),
        isCompleted = todoItem.isCompleted,
        createDate = mapLongToDate(todoItem.createDate)!!,
        changedDate = mapLongToDate(todoItem.changedDate)
    )

    private fun toDto(todoItem: Todo) = TodoDto(
        id = todoItem.id,
        msg = todoItem.msg,
        priority = mapPriorityToString(todoItem.priority),
        deadline = mapDateToLong(todoItem.deadline),
        isCompleted = todoItem.isCompleted,
        color = null,
        createDate = mapDateToLong(todoItem.createDate)?: 0L,
        changedDate = mapDateToLong(todoItem.changedDate)?: 0L,
        device = Build.DEVICE
    )

    fun toListEntity(list: List<TodoDto>?) = list?.map {
        toEntity(it)
    }
    private fun toListDto(list: List<Todo>) = list.map {
        toDto(it)
    }

    fun toListRequestDto(list: List<Todo>) = TodoListRequest(toListDto(list))

    fun toRequestDto(todo: Todo) = TodoRequest(toDto(todo))

    private fun mapStringToPriority(priority: String) =
         when(priority){
            "low" -> Todo.Priority.LOW
            "basic" -> Todo.Priority.NORMAL
            "important" -> Todo.Priority.URGENT
            else -> Todo.Priority.NORMAL
        }

    private fun mapPriorityToString(priority: Todo.Priority) =
        when(priority){
            Todo.Priority.LOW -> "low"
            Todo.Priority.NORMAL -> "basic"
            Todo.Priority.URGENT -> "important"
        }


    private fun mapLongToDate(time: Long?): Date? {
        return if (time == null)
            null
        else {
            val date = Date()
            date.time = time
            date
        }
    }
    private fun mapDateToLong(date: Date?) = date?.time

}