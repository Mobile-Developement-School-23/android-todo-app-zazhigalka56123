package i.need.drugs.todoapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class TodoListRequest(
    @SerializedName("list") val list: List<TodoDto>)
