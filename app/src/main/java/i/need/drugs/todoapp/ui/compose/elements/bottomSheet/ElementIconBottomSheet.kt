package i.need.drugs.todoapp.ui.compose.elements.bottomSheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import i.need.drugs.todoapp.ui.compose.theme.colors

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIconBottomSheet(
    closeBottomSheet: () -> Unit,
    action: () -> Unit,
    scope: CoroutineScope,
    bottomSheetState: SheetState,
    imageVector: ImageVector,
    save: Boolean
) {
    IconButton(
        onClick = {
            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                if (!bottomSheetState.isVisible) {
                    if (save) action()
                    closeBottomSheet()
                }
            }
        }
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "",
            tint = colors.labelPrimary
        )
    }
}