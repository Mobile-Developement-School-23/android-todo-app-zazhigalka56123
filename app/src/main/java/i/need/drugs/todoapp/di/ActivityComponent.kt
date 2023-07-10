package i.need.drugs.todoapp.di

import dagger.Subcomponent
import i.need.drugs.todoapp.presentation.AddTodoFragment
import i.need.drugs.todoapp.presentation.EditTodoFragment
import i.need.drugs.todoapp.presentation.MainActivity
import i.need.drugs.todoapp.presentation.MainFragment

@Subcomponent(
    modules = [ViewModelModule::class]
)
interface ActivityComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: MainFragment)
    fun inject(fragment: AddTodoFragment)
    fun inject(fragment: EditTodoFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }


}