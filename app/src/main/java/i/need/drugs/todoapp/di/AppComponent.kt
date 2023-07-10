package i.need.drugs.todoapp.di

import android.app.Activity
import android.app.Application
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import i.need.drugs.todoapp.presentation.AddTodoFragment
import i.need.drugs.todoapp.presentation.EditTodoFragment
import i.need.drugs.todoapp.presentation.MainActivity
import i.need.drugs.todoapp.presentation.MainFragment

@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: MainFragment)
    fun inject(fragment: AddTodoFragment)
    fun inject(fragment: EditTodoFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ) : AppComponent
    }
}