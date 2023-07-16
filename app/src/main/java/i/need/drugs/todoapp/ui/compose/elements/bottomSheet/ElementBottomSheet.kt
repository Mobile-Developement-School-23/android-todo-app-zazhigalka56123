package i.need.drugs.todoapp.ui.compose.elements.bottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import i.need.drugs.todoapp.domain.model.Todo
import i.need.drugs.todoapp.ui.compose.TodoState
import i.need.drugs.todoapp.ui.compose.theme.colors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBottomSheet(
    action: (TodoState) -> Unit,
    closeBottomSheet: () -> Unit,
    oldPriority: Todo.Priority,
    show: Boolean
) {
    val scope = rememberCoroutineScope()
    val skipPartially by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartially)
    var priority by remember { mutableStateOf(oldPriority) }

    if (show) {
        ModalBottomSheet(
            onDismissRequest = { closeBottomSheet() },
            sheetState = bottomSheetState,
            containerColor = colors.backPrimary,
            modifier = Modifier
                .fillMaxHeight(0.25f)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AddIconBottomSheet(
                        closeBottomSheet = { closeBottomSheet() },
                        action = { action(TodoState.ChangedPriority(priority)) },
                        scope = scope,
                        bottomSheetState = bottomSheetState,
                        imageVector = Icons.Default.Close,
                        save = false
                    )
                    AddIconBottomSheet(
                        closeBottomSheet = { closeBottomSheet() },
                        action = { action(TodoState.ChangedPriority(priority)) },
                        scope = scope,
                        bottomSheetState = bottomSheetState,
                        imageVector = Icons.Default.Done,
                        save = true
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AddItemBottomSheet(
                        changePriority = { priority = Todo.Priority.LOW },
                        priority = Todo.Priority.LOW,
                        checked = priority == Todo.Priority.LOW
                    )
                    AddItemBottomSheet(
                        changePriority = { priority = Todo.Priority.NORMAL },
                        priority = Todo.Priority.NORMAL,
                        checked = priority == Todo.Priority.NORMAL
                    )
                    AddItemBottomSheet(
                        changePriority = { priority = Todo.Priority.URGENT },
                        priority = Todo.Priority.URGENT,
                        checked = priority == Todo.Priority.URGENT
                    )
                }
            }
        }
    }

}