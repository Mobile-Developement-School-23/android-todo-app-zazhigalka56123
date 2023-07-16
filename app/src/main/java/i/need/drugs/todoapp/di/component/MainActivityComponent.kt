package i.need.drugs.todoapp.di.component

import dagger.Subcomponent
import i.need.drugs.todoapp.di.MainActivityScope
import i.need.drugs.todoapp.ui.AddTodoFragment
import i.need.drugs.todoapp.ui.AddTodoXmlFragment
import i.need.drugs.todoapp.ui.EditTodoFragment
import i.need.drugs.todoapp.ui.MainFragment

@MainActivityScope
@Subcomponent
interface MainActivityComponent {

    fun inject(fragment: MainFragment)

    fun inject(fragment: AddTodoXmlFragment)
    fun inject(fragment: AddTodoFragment)

    fun inject(fragment: EditTodoFragment)

}