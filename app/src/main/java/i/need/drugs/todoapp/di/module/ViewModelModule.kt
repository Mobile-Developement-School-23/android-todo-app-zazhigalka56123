package i.need.drugs.todoapp.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import i.need.drugs.todoapp.ui.MainViewModel
import i.need.drugs.todoapp.ui.TodoViewModel


@Module
interface ViewModelModule {

    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    fun bindTodoViewModel(viewModel: TodoViewModel): ViewModel
}