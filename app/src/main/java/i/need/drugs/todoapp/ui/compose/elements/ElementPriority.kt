package i.need.drugs.todoapp.ui.compose.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import i.need.drugs.todoapp.R
import i.need.drugs.todoapp.domain.model.Todo
import i.need.drugs.todoapp.ui.compose.TodoState
import i.need.drugs.todoapp.ui.compose.elements.bottomSheet.AddBottomSheet
import i.need.drugs.todoapp.ui.compose.theme.Red
import i.need.drugs.todoapp.ui.compose.theme.ThemePreview
import i.need.drugs.todoapp.ui.compose.theme.colors
import i.need.drugs.todoapp.ui.compose.theme.theme

@Composable
fun AddElementPriority(
    priority: Todo.Priority,
    action: (TodoState) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .padding(top = 20.dp, bottom = 15.dp)
            .clip(RoundedCornerShape(5.dp))
            .clickable { showBottomSheet = !showBottomSheet }
    ) {
        Text(
            text = stringResource(id = R.string.priority_title),
            color = colors.labelPrimary
        )
        Text(
            text = stringResource(
                id = when(priority){
                    Todo.Priority.LOW -> R.string.low_priority
                    Todo.Priority.NORMAL -> R.string.normal_priority
                    Todo.Priority.URGENT -> R.string.urgent_priority
                }
            ),
            modifier = Modifier.padding(top = 5.dp),
            color = if (priority == Todo.Priority.URGENT) Red else colors.labelTertiary
        )
        AddBottomSheet(
            action = action,
            closeBottomSheet = { showBottomSheet = false },
            oldPriority = priority,
            show = showBottomSheet
        )
    }
}
@Preview
@Composable
fun PreviewElementPriority(
    @PreviewParameter(ThemePreview::class) darkTheme: Boolean
) {
    theme(darkTheme = darkTheme) {
        AddElementPriority(
            priority = Todo.Priority.LOW,
            {}
        )
    }
}
