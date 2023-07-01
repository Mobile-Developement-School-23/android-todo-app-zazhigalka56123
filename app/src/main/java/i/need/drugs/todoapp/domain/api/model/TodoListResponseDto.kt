package i.need.drugs.todoapp.domain.api.model

import com.google.gson.annotations.SerializedName

data class TodoListResponseDto(
    @SerializedName("status")
    val status: String,
    @SerializedName("list")
    val list: List<TodoItemDto>,
    @SerializedName("revision")
    val revision: Int
)
