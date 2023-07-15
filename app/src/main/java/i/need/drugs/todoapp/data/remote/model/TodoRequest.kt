package i.need.drugs.todoapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class TodoRequest (
    @SerializedName("element")
    val element: TodoDto
)