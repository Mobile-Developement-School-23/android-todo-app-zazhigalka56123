package i.need.drugs.todoapp.ui.compose.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import i.need.drugs.todoapp.R
import i.need.drugs.todoapp.ui.compose.TodoState
import i.need.drugs.todoapp.ui.compose.theme.ThemePreview
import i.need.drugs.todoapp.ui.compose.theme.colors
import i.need.drugs.todoapp.ui.compose.theme.theme

@Composable
fun AddTextFieldElement(
    text: String,
    action: (TodoState) -> Unit
) {
    BasicTextField(
        value = text,
        onValueChange = { action(TodoState.ChangedText(it))},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .background(
                color = colors.backSecondary,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp)),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = colors.labelPrimary
        ),
        cursorBrush = SolidColor(Color.Blue),
    ) { textField ->
        Box(
            modifier = Modifier.padding(all = 15.dp)
        ) {
            if (text.isEmpty())
                Text(
                    text = stringResource(R.string.msg_hint),
                    color = colors.labelTertiary
                )
            else
                Text(
                    text = text,
                    color = colors.labelPrimary
                )
            textField.invoke()
        }
    }
}

@Preview
@Composable
fun PreviewAddElementPriority(
    @PreviewParameter(ThemePreview::class) darkTheme: Boolean
) {
    theme(darkTheme = darkTheme) {
        AddTextFieldElement(
            text = "",
            {}
        )
    }
}