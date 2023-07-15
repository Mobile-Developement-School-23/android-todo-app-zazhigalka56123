package i.need.drugs.todoapp.domain.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import i.need.drugs.todoapp.TodoApp
import i.need.drugs.todoapp.data.local.LocalDataSourceImpl
import i.need.drugs.todoapp.domain.Constant.EXTRA_ID
import i.need.drugs.todoapp.domain.model.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class DeferredNotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var coroutineScope: CoroutineScope

    @Inject
    lateinit var repository: NotificationRepository

    @Inject
    lateinit var localDataSourceImpl: LocalDataSourceImpl

    override fun onReceive(context: Context, intent: Intent) {
        (context.applicationContext as TodoApp).component.inject(this)
        coroutineScope.launch {
            val id = UUID.fromString(intent.extras?.getString(EXTRA_ID))
            if (id != null) {
                val manager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.cancel(id.hashCode())

                val todo = localDataSourceImpl.getTodo(id).todo

                if (todo != null){
                    updateTodo(todo)
                }
            }
        }
    }

    private suspend fun updateTodo(todo: Todo){
        val deadline = todo.deadline
        if (deadline != null){
            localDataSourceImpl.editTodo(
                todo.copy(deadline = Date(deadline.time.plus(24 * 60 * 60 * 1000)))
            )
        }
    }
}