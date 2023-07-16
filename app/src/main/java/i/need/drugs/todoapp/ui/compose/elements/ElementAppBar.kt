package i.need.drugs.todoapp.ui.compose.elements

import androidx.compose.animation.animateColorAsState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import i.need.drugs.todoapp.R
import i.need.drugs.todoapp.domain.model.Todo
import i.need.drugs.todoapp.ui.compose.TodoState
import i.need.drugs.todoapp.ui.compose.theme.Blue
import i.need.drugs.todoapp.ui.compose.theme.ThemePreview
import i.need.drugs.todoapp.ui.compose.theme.colors
import i.need.drugs.todoapp.ui.compose.theme.theme
import java.util.Date
import java.util.UUID

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AddAppBarElement(
    todo: Todo,
    state: (TodoState) -> Unit
){
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = { state(TodoState.Close) }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                    contentDescription = "stringResource(R.string.close_button)"

                )
            }
        },
        actions = {
            val saveButtonColor by animateColorAsState(
                targetValue = if (todo.msg.isBlank()) colors.labelDisable else Blue,
                label = "save_button_color_animation"
            )
            TextButton(
                onClick = {
                    state(TodoState.AddTodo)
                  },
                enabled = todo.msg.isNotBlank(),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = saveButtonColor,
                    disabledContentColor = saveButtonColor
                )
            ) {
                Text(
                    text = stringResource(R.string.save),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colors.backPrimary,
            navigationIconContentColor = colors.labelPrimary
        )
    )

}

@Preview
@Composable
fun PreviewAppBarElement(
    @PreviewParameter(ThemePreview::class) darkTheme: Boolean
) {
    theme(darkTheme = darkTheme) {
        AddAppBarElement(
            Todo(
                UUID.randomUUID(),
                "",
                Todo.Priority.LOW,
                Date(),
                false,
                Date(Date().time),
                null
            ),
            {}

        )
    }
}
