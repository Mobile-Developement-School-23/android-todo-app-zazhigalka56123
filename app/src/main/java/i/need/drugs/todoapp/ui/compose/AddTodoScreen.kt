package i.need.drugs.todoapp.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import i.need.drugs.todoapp.domain.model.Todo
import i.need.drugs.todoapp.ui.compose.elements.AddTextFieldElement
import i.need.drugs.todoapp.ui.compose.elements.AddAppBarElement
import i.need.drugs.todoapp.ui.compose.elements.AddDeadlineElement
import i.need.drugs.todoapp.ui.compose.elements.AddElementPriority
import i.need.drugs.todoapp.ui.compose.elements.AddLineElement

import i.need.drugs.todoapp.ui.compose.theme.ThemePreview
import i.need.drugs.todoapp.ui.compose.theme.colors
import i.need.drugs.todoapp.ui.compose.theme.theme
import java.util.Date
import java.util.UUID

@Composable
fun AddTodoScreen (
    todo: Todo,
    state: (TodoState) -> Unit
) {
    Scaffold(topBar = { AddAppBarElement(todo, state) }, containerColor = colors.backPrimary) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item  {
                AddTextFieldElement(todo.msg, state)
                AddElementPriority(todo.priority, state)
                AddLineElement()
                AddDeadlineElement(todo, state)
                AddLineElement()

            }
        }
    }
}

@Preview
@Composable
fun PreviewAddTodoItemScreen(
    @PreviewParameter(ThemePreview::class) darkTheme: Boolean
) {
    val state = Todo(
        id = UUID.randomUUID(),
        msg = "Text",
        priority = Todo.Priority.LOW,
        deadline = Date(),
        createDate = Date(Date().time),
        changedDate = null,
        isCompleted = false

    )
    theme(darkTheme = darkTheme) {
        AddTodoScreen(state, {})
    }
}
