package i.need.drugs.todoapp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import i.need.drugs.todoapp.presentation.AddTodoFragment
import i.need.drugs.todoapp.presentation.EditTodoFragment
import i.need.drugs.todoapp.presentation.MainActivity
import i.need.drugs.todoapp.presentation.MainFragment

@Component(modules = [DataModule::class])
interface AppComponent {
    fun activityComponentFactory(): ActivityComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ) : AppComponent
    }
}