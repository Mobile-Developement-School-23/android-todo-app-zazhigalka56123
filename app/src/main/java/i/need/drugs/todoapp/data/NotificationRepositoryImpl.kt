package i.need.drugs.todoapp.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import i.need.drugs.todoapp.domain.Constant.EXTRA_ID
import i.need.drugs.todoapp.domain.model.Todo
import i.need.drugs.todoapp.domain.notification.NotificationReceiver
import i.need.drugs.todoapp.domain.notification.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val context: Context
): NotificationRepository {

    private val manager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun setNotification(todo: Todo) {
        val deadline = todo.deadline
        if (deadline != null && !todo.isCompleted){
            val intent = Intent(context, NotificationReceiver::class.java).apply {
                putExtra(EXTRA_ID, todo.id.toString())
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                todo.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            manager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                deadline.time,
                pendingIntent
            )
        }
    }

    override fun deleteNotification(todo: Todo) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            todo.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        manager.cancel(pendingIntent)
    }
}