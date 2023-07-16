package i.need.drugs.todoapp.ui.compose

import i.need.drugs.todoapp.domain.model.Todo

sealed class TodoState {

    data class ChangedText(val text: String) : TodoState()

    data class ChangedDeadline(val deadline: Long?) : TodoState()

    data class ChangedPriority(val priority: Todo.Priority) : TodoState()

    object Add: TodoState()

    object Delete : TodoState()

    object Close: TodoState()

}
