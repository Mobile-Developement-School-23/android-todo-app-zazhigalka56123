package i.need.drugs.todoapp.data.util

import i.need.drugs.todoapp.domain.model.Todo
import java.util.UUID
import javax.inject.Inject

class Merger @Inject constructor() {

    fun merge(l1: List<Todo>, l2: List<Todo>): List<Todo>{
        val list = mutableListOf<Todo>()

        l1.forEach { item1 ->
            val pair = checkInList(item1.id, l2)
            if (pair.first && pair.second != null){
                list.add(mergeItems(item1, pair.second!!))
            }
        }
        l2.forEach { item2 ->
            val pair = checkInList(item2.id, l1)
            if (!pair.first){
                list.add(item2)
            }
        }

        return list
    }

    private fun checkInList(id: UUID, list: List<Todo>): Pair<Boolean, Todo?> {
        list.forEach {item ->
            if (item.id == id)
                return Pair(true, item)
        }
        return Pair(false, null)
    }

    private fun mergeItems(i1: Todo, i2: Todo): Todo {
        val changeDate1 = i1.changedDate
        val createDate1 = i1.createDate

        val changeDate2 = i2.changedDate
        val createDate2 = i2.createDate

        val date1 = if (changeDate1 != null) maxOf(changeDate1, createDate1) else createDate1
        val date2 = if (changeDate2 != null) maxOf(changeDate2, createDate2) else createDate2

        if (date1 < date2) {
            i1.msg = i2.msg
            i1.deadline = i2.deadline
            i1.priority = i2.priority
            i1.isCompleted = i2.isCompleted
            i1.changedDate = i2.changedDate
        }

        return i1
    }

}