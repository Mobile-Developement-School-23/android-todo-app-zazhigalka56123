package i.need.drugs.todoapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class TodoResponse (
    @SerializedName("status")
    val status: String,
    @SerializedName("element")
    val element: TodoDto,
    @SerializedName("revision")
    val revision: Int
)