package i.need.drugs.todoapp.presentation

import android.app.Application
import i.need.drugs.todoapp.di.DaggerAppComponent

class TodoApp : Application() {

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}