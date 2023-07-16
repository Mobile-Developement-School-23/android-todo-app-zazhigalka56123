package i.need.drugs.todoapp.ui.compose.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import i.need.drugs.todoapp.R
import i.need.drugs.todoapp.ui.compose.TodoState
import i.need.drugs.todoapp.ui.compose.theme.ThemePreview
import i.need.drugs.todoapp.ui.compose.theme.colors
import i.need.drugs.todoapp.ui.compose.theme.theme

@Composable
fun AddDeleteElement(
    state: (TodoState) -> Unit
) {
    TextButton(
        onClick = { state(TodoState.Delete) },
        modifier = Modifier.padding(horizontal = 5.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color.Red,
            disabledContentColor = colors.labelDisable
        )
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
            contentDescription = stringResource(R.string.delete),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = stringResource(R.string.delete),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}
@Preview
@Composable
fun PreviewAddTodoItemDelete(
    @PreviewParameter(ThemePreview::class) darkTheme: Boolean
) {
    theme(darkTheme = darkTheme) {
        AddDeleteElement({})
    }
}
