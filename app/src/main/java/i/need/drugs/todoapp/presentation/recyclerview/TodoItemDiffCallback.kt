package i.need.drugs.todoapp.presentation.recyclerview

import androidx.recyclerview.widget.DiffUtil
import i.need.drugs.todoapp.domain.TodoItem

class TodoItemDiffCallback : DiffUtil.ItemCallback<TodoItem>() {

    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem) = oldItem == newItem
}