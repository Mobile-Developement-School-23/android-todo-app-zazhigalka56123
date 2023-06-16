package i.need.drugs.todoapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import i.need.drugs.todoapp.domain.TodoItem
import i.need.drugs.todoapp.domain.TodoListRepository
import java.util.Calendar
import java.util.Date

object TodoListRepositoryImpl : TodoListRepository {

    private val todoList = mutableListOf<TodoItem>()
    private val todoListLiveData = MutableLiveData<List<TodoItem>>()

    init {
        todoList.add(
            TodoItem(
                "0",
                "Доделать домашку по алгосам",
                TodoItem.ItemPriority.LOW,
                isCompleted = false,
                createDate = Calendar.getInstance().time,
                deadline = null,
                changedDate = null
            )
        )
        todoList.add(
            TodoItem(
                "1",
                "Сходить в магазин за продуктами, купить: пиво, чипсы, хлеб, молоко, " +
                        "овсяное печенье, колу в пластиковой бутылке, яйца, помидоры, рис," +
                        " курицу, шоколад с фольгой, колбасу, зелень (1.5 грамма)",
                TodoItem.ItemPriority.URGENT,
                isCompleted = false,
                createDate = Calendar.getInstance().time,
                deadline = null,
                changedDate = null
            )
        )
        todoList.add(
            TodoItem(
                "2",
                "Найти клад 55°45′06′′, 37°37′04′′ ",
                TodoItem.ItemPriority.URGENT,
                isCompleted = false,
                createDate = Calendar.getInstance().time,
                deadline = null,
                changedDate = null
            )
        )
        todoList.add(
            TodoItem(
                "3",
                "Посмотреть телевизор ",
                TodoItem.ItemPriority.URGENT,
                isCompleted = false,
                createDate = Calendar.getInstance().time,
                deadline = null,
                changedDate = null
            )
        )
        todoList.add(
            TodoItem(
                "4",
                "Перевести бабушку через дорогу",
                TodoItem.ItemPriority.LOW,
                isCompleted = false,
                createDate = Calendar.getInstance().time,
                deadline = null,
                changedDate = null
            )
        )
        todoList.add(
            TodoItem(
                "5",
                "Отклеить этикетки с бананов",
                TodoItem.ItemPriority.URGENT,
                isCompleted = false,
                createDate = Calendar.getInstance().time,
                deadline = null,
                changedDate = null
            )
        )
        todoList.add(
            TodoItem(
                "6",
                "Уехать в Воронеж",
                TodoItem.ItemPriority.LOW,
                isCompleted = false,
                createDate = Calendar.getInstance().time,
                deadline = null,
                changedDate = null
            )
        )
        todoList.add(
            TodoItem(
                "7",
                "Перестать думать что я воспаленный мозг джека",
                TodoItem.ItemPriority.NORMAL,
                isCompleted = false,
                createDate = Calendar.getInstance().time,
                deadline = null,
                changedDate = null
            )
        )
        todoList.add(
            TodoItem(
                "8",
                "Обеспечить безопасность президента",
                TodoItem.ItemPriority.LOW,
                isCompleted = false,
                createDate = Calendar.getInstance().time,
                deadline = null,
                changedDate = null
            )
        )
        todoList.add(
            TodoItem(
                "9",
                "Прекратить поставки оружия в Афганистан",
                TodoItem.ItemPriority.LOW,
                isCompleted = false,
                createDate = Calendar.getInstance().time,
                deadline = null,
                changedDate = null
            )
        )
        todoList.add(
            TodoItem(
                "10",
                "Устроить гос переворот в Восточном Тиморе",
                TodoItem.ItemPriority.URGENT,
                isCompleted = false,
                createDate = Calendar.getInstance().time,
                deadline = null,
                changedDate = null
            )
        )

        updateTodoList()
    }

    override fun getTodoList(): LiveData<List<TodoItem>> {
        return todoListLiveData
    }

    override fun getTodoItem(id: String): TodoItem {
        return todoList.find { it.id == id } ?: throw RuntimeException("not found")
    }

    override fun editTodoItem(item: TodoItem) {
        val oldElement = todoList.find { it.id == item.id }
        val index = todoList.indexOf(oldElement)

        todoList[index] = item

        updateTodoList()
    }

    override fun addTodoItem(item: TodoItem) {
        todoList.add(item)

        updateTodoList()
    }

    override fun deleteTodoItem(item: TodoItem) {
        todoList.remove(item)

        updateTodoList()
    }


    private fun updateTodoList() {
        todoListLiveData.value = todoList.toList()
    }
}