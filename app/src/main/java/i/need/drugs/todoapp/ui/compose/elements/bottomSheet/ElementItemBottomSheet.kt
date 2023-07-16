package i.need.drugs.todoapp.ui.compose.elements.bottomSheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import i.need.drugs.todoapp.R
import i.need.drugs.todoapp.domain.model.Todo

import i.need.drugs.todoapp.ui.compose.theme.Red
import i.need.drugs.todoapp.ui.compose.theme.colors

@Composable
fun AddItemBottomSheet(
    changePriority: () -> Unit,
    priority: Todo.Priority,
    checked: Boolean
) {
    val color = when {
        checked && priority == Todo.Priority.URGENT -> Red
        checked -> colors.labelPrimary
        else -> colors.labelTertiary
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { changePriority() }
    ) {
        Text(
            text = stringResource(
                id = when(priority){
                    Todo.Priority.LOW -> R.string.low_priority
                    Todo.Priority.NORMAL -> R.string.normal_priority
                    Todo.Priority.URGENT -> R.string.urgent_priority
                }
            ),
            modifier = Modifier.padding(all = 10.dp),
            color = color,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}