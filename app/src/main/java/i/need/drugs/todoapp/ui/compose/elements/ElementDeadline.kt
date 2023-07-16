package i.need.drugs.todoapp.ui.compose.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import i.need.drugs.todoapp.R
import i.need.drugs.todoapp.domain.model.Todo
import i.need.drugs.todoapp.ui.compose.TodoState
import i.need.drugs.todoapp.ui.compose.theme.Blue
import i.need.drugs.todoapp.ui.compose.theme.BlueTranslucent

import i.need.drugs.todoapp.ui.compose.theme.ThemePreview
import i.need.drugs.todoapp.ui.compose.theme.colors
import i.need.drugs.todoapp.ui.compose.theme.theme
import java.util.Calendar
import java.util.Date
import java.util.UUID

@Composable
fun AddDeadlineElement(
    todo: Todo,
    state: (TodoState) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var isDialogOpen by remember { mutableStateOf(false) }
        var switchState by remember { mutableStateOf(todo.deadline != null) }

        Column {
            Text(
                text = stringResource(id = R.string.deadline_title),
                modifier = Modifier.padding(start = 5.dp),
                color = colors.labelPrimary
            )
            AnimatedVisibility(visible = switchState) {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable { isDialogOpen = true },

                ) {
                    Text(
                        text = formatText(todo),
                        color = Blue,
                    )
                }
            }

        }
        Switch(
            checked = switchState,
            onCheckedChange = { checked ->
                if (checked){
                    switchState = true
                    todo.deadline = Calendar.getInstance().time
                    state(TodoState.ChangedDeadline(todo.deadline?.time))
                }else{
                    state(TodoState.ChangedDeadline(null))
                    switchState = false
                }
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Blue,
                checkedTrackColor = BlueTranslucent,
                uncheckedThumbColor = colors.backElevated,
                uncheckedTrackColor = colors.supportOverlay,
                uncheckedBorderColor = colors.supportOverlay,
            )
        )
        DatePicker(
            show = isDialogOpen,
            date = todo.deadline ?: Calendar.getInstance().time,
            state = state,
            closeDialog = { isDialogOpen = false }
        )


    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePicker(
    show: Boolean,
    date: Date,
    state: (TodoState) -> Unit,
    closeDialog: () -> Unit
) {
    if (show) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = date.time
        )
        val confirmEnabled by remember(datePickerState.selectedDateMillis) {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }

        DatePickerDialog(
            onDismissRequest = closeDialog,
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            state(TodoState.ChangedDeadline(it))
                        }
                        closeDialog()
                    },
                    enabled = confirmEnabled
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = closeDialog
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

private fun formatText(todo: Todo): String{
    val c = Calendar.getInstance()
    val time = todo.deadline
    if (time != null) {
        c.time = time
    }
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH) + 1
    val day = c.get(Calendar.DAY_OF_MONTH)

    return "$day.$month.$year"

}

@Preview
@Composable
fun PreviewDeadlineElement(
    @PreviewParameter(ThemePreview::class) darkTheme: Boolean
) {
    theme(darkTheme = darkTheme) {
        AddDeadlineElement(
            todo = Todo(
                id = UUID.randomUUID(),
                msg = "Text",
                priority = Todo.Priority.LOW,
                deadline = Date(),
                createDate = Date(Date().time),
                changedDate = null,
                isCompleted = false

            ),
            {}
        )
    }
}