package i.need.drugs.todoapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class TodoListResponse (
    @SerializedName("status")
    val status: String,
    @SerializedName("list")
    val list: List<TodoDto>,
    @SerializedName("revision")
    val revision: Int
)