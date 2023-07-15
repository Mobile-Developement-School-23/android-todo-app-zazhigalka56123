package i.need.drugs.todoapp.domain.notification

import i.need.drugs.todoapp.domain.model.Todo

interface NotificationRepository {

    fun setNotification(todo: Todo)

    fun deleteNotification(todo: Todo)
}