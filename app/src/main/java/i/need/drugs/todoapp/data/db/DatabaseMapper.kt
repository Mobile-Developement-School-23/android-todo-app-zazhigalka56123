package i.need.drugs.todoapp.data.db

import i.need.drugs.todoapp.domain.TodoItem
import java.util.Date

class DatabaseMapper {

    fun mapEntityToDbModel(todoItem: TodoItem) = TodoItemDbModel(
        id = todoItem.id,
        msg = todoItem.msg,
        priority = mapItemPriorityToInt(todoItem.priority),
        deadline = mapDateToLong(todoItem.deadline),
        isCompleted = todoItem.isCompleted,
        createDate = mapDateToLong(todoItem.createDate)!!,
        changedDate = mapDateToLong(todoItem.changedDate)
    )

    fun mapDbModelToEntity(todoItemDbModel: TodoItemDbModel) = TodoItem(
        id = todoItemDbModel.id,
        msg = todoItemDbModel.msg,
        priority = mapIntToItemPriority(todoItemDbModel.priority),
        deadline = mapLongToDate(todoItemDbModel.deadline),
        isCompleted = todoItemDbModel.isCompleted,
        createDate = mapLongToDate(todoItemDbModel.createDate)!!,
        changedDate = mapLongToDate(todoItemDbModel.changedDate)
    )

    fun mapTodoListDbModelToTodoListEntity(list: List<TodoItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
    fun mapTodoListEntityToTodoListDbModel(list: List<TodoItem>) = list.map {
        mapEntityToDbModel(it)
    }
    private fun mapItemPriorityToInt(priority: TodoItem.ItemPriority) =
        when (priority){
            TodoItem.ItemPriority.LOW -> 0
            TodoItem.ItemPriority.NORMAL -> 1
            TodoItem.ItemPriority.URGENT -> 2
        }
    private fun mapIntToItemPriority(priority: Int) =
        when (priority){
            0 -> TodoItem.ItemPriority.LOW
            1 -> TodoItem.ItemPriority.NORMAL
            2 -> TodoItem.ItemPriority.URGENT
            else -> TodoItem.ItemPriority.NORMAL
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