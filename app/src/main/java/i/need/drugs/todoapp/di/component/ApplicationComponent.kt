package i.need.drugs.todoapp.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import i.need.drugs.todoapp.TodoApp
import i.need.drugs.todoapp.di.module.AppModule
import i.need.drugs.todoapp.di.ApplicationScope
import i.need.drugs.todoapp.di.module.DataModule
import i.need.drugs.todoapp.di.module.NetworkModule
import i.need.drugs.todoapp.di.module.RepositoryModule
import i.need.drugs.todoapp.di.module.ViewModelModule
import i.need.drugs.todoapp.domain.notification.DeferredNotificationReceiver
import i.need.drugs.todoapp.domain.notification.NotificationReceiver
import i.need.drugs.todoapp.ui.MainActivity

@ApplicationScope
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        RepositoryModule::class
    ]
)
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun mainActivityComponent(): MainActivityComponent

    fun inject(activity: MainActivity)

    fun inject(app: TodoApp)

    fun inject(receiver: NotificationReceiver)

    fun inject(receiver: DeferredNotificationReceiver)
}