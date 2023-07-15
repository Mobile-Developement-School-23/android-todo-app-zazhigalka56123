package i.need.drugs.todoapp.ui

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import i.need.drugs.todoapp.di.MainActivityScope
import i.need.drugs.todoapp.domain.usecases.AddTodoUseCase
import i.need.drugs.todoapp.domain.usecases.DeleteTodoUseCase
import i.need.drugs.todoapp.domain.usecases.DownloadTodoListUseCase
import i.need.drugs.todoapp.domain.usecases.EditTodoUseCase
import i.need.drugs.todoapp.domain.usecases.GetTodoListUseCase
import i.need.drugs.todoapp.domain.usecases.GetTodoUseCase
import i.need.drugs.todoapp.domain.usecases.UpdateTodoListUseCase
import java.lang.IllegalArgumentException
import javax.inject.Inject

@MainActivityScope
class ViewModelFactory @Inject constructor(
    private val getTodoListUseCase: GetTodoListUseCase,
    private val downloadTodoListUseCase: DownloadTodoListUseCase,
    private val getTodoUseCase: GetTodoUseCase,
    private val editTodoUseCase: EditTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val updateTodoListUseCase: UpdateTodoListUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    val connectManager: ConnectivityManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when(modelClass) {
            MainViewModel::class.java -> MainViewModel(
                getTodoListUseCase,
                downloadTodoListUseCase,
                editTodoUseCase,
                deleteTodoUseCase,
                updateTodoListUseCase,
                addTodoUseCase,
                connectManager
            ) as T
            TodoViewModel::class.java -> TodoViewModel(
                getTodoUseCase,
                deleteTodoUseCase,
                addTodoUseCase,
                editTodoUseCase,
            ) as T
            else -> throw IllegalArgumentException("something wrong with ViewModel")

        }
}