package i.need.drugs.todoapp.ui.recyclerview

import androidx.recyclerview.widget.DiffUtil
import i.need.drugs.todoapp.domain.model.Todo

class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {

    override fun areItemsTheSame(oldItem: Todo, newItem: Todo) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo) = oldItem == newItem
}