package i.need.drugs.todoapp.data

data class ResponseData<T>(
    val code: Int,
    val todo: T?
)