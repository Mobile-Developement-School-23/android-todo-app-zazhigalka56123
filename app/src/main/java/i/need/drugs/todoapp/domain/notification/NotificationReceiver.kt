package i.need.drugs.todoapp.domain.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import i.need.drugs.todoapp.R
import i.need.drugs.todoapp.TodoApp
import i.need.drugs.todoapp.data.local.LocalDataSourceImpl
import i.need.drugs.todoapp.domain.Constant.CHANNEL_ID
import i.need.drugs.todoapp.domain.Constant.CHANNEL_NAME
import i.need.drugs.todoapp.domain.Constant.EXTRA_ID
import i.need.drugs.todoapp.domain.model.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class NotificationReceiver : BroadcastReceiver() {
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
                val todo = localDataSourceImpl.getTodo(id).todo

                if (todo != null){
                    receive(todo, context)
                }
            }
        }
    }

    private fun receive(todo: Todo, context: Context){
        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.createNotificationChannel(
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        )
        val notification = Notification.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_check)
            .setContentTitle(getTitle(todo.priority))
            .setContentText(todo.msg)
            .setAutoCancel(true)
            .setContentIntent(getContentIntent(context, todo.id.toString()))
            .addAction(getAction(context, todo.id.toString()))
            .build()

        repository.deleteNotification(todo)
        manager.notify(todo.id.hashCode(), notification)
    }

    private fun getTitle(priority: Todo.Priority): String{
        return when(priority) {
            Todo.Priority.LOW -> "Дедлайн по неважному делу"
            Todo.Priority.URGENT -> "Дедлайн по важному делу"
            Todo.Priority.NORMAL -> "Дедлайн по обычному делу"
        }
    }

    private fun getAction(context: Context, id: String) =
        Notification.Action.Builder(
            R.drawable.ic_close,
            "Отложить на день",
            getDeferredIntent(context, id),
        ).build()

    private fun getContentIntent(context: Context, id: String) =
        NavDeepLinkBuilder(context).setGraph(R.navigation.navigation_graph_main)
            .setDestination(R.id.fragmentEditTodo).setArguments(bundleOf("todoId" to id))
            .createPendingIntent()

    private fun getDeferredIntent(context: Context, id: String): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            id.hashCode(),
            Intent(context, DeferredNotificationReceiver::class.java).apply {
                putExtra(EXTRA_ID, id)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
}


