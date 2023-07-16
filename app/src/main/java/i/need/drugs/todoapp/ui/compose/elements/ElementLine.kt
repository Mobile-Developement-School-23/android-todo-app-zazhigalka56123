package i.need.drugs.todoapp.ui.compose.elements

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp

import i.need.drugs.todoapp.ui.compose.theme.ThemePreview
import i.need.drugs.todoapp.ui.compose.theme.colors
import i.need.drugs.todoapp.ui.compose.theme.theme

@Composable
fun AddLineElement (){
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(16.dp)),
        color = colors.supportSeparator
    )
}
@Preview
@Composable
fun PreviewLineElement(
    @PreviewParameter(ThemePreview::class) darkTheme: Boolean
) {
    theme(darkTheme = darkTheme) {
        AddLineElement()
    }
}
