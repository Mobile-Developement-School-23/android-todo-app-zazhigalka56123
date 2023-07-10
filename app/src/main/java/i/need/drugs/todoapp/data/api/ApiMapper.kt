package i.need.drugs.todoapp.data.api

import android.os.Build
import i.need.drugs.todoapp.domain.api.model.TodoItemDto
import i.need.drugs.todoapp.domain.api.model.TodoItemRequestDto
import i.need.drugs.todoapp.domain.api.model.TodoListRequestDto
import i.need.drugs.todoapp.domain.db.TodoItem
import java.util.*
import javax.inject.Inject

class ApiMapper @Inject constructor() {

    fun mapDtoToEntity(todoItem: TodoItemDto) = TodoItem(
        id = todoItem.id.toString(),
        msg = todoItem.msg,
        priority = mapStringToPriority(todoItem.priority),
        deadline = mapLongToDate(todoItem.deadline),
        isCompleted = todoItem.isCompleted,
        createDate = mapLongToDate(todoItem.createDate)!!,
        changedDate = mapLongToDate(todoItem.changedDate)
    )

    fun mapEntityToDto(todoItem: TodoItem) = TodoItemDto(
        id = UUID.fromString(todoItem.id),
        msg = todoItem.msg,
        priority = mapPriorityToString(todoItem.priority),
        deadline = mapDateToLong(todoItem.deadline),
        isCompleted = todoItem.isCompleted,
        color = null,
        createDate = mapDateToLong(todoItem.createDate)?: 0L,
        changedDate = mapDateToLong(todoItem.changedDate)?: 0L,
        device = Build.DEVICE
    )

    fun mapListDtoToListEntity(list: List<TodoItemDto>) = list.map {
        mapDtoToEntity(it)
    }

    fun mapListEntityToListRequestDto(list: List<TodoItem>) = TodoListRequestDto(
        list = list.map {mapEntityToDto(it)}
    )

    fun mapEntityToItemRequestDto(item: TodoItem) = TodoItemRequestDto(
        element = mapEntityToDto(item)
    )

    private fun mapStringToPriority(priority: String) =
         when(priority){
            "low" -> TodoItem.ItemPriority.LOW
            "basic" -> TodoItem.ItemPriority.NORMAL
            "important" -> TodoItem.ItemPriority.URGENT
            else -> TodoItem.ItemPriority.NORMAL
        }

    private fun mapPriorityToString(priority: TodoItem.ItemPriority) =
        when(priority){
            TodoItem.ItemPriority.LOW -> "low"
            TodoItem.ItemPriority.NORMAL -> "basic"
            TodoItem.ItemPriority.URGENT -> "important"
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