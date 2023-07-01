package i.need.drugs.todoapp.domain.api.model

import com.google.gson.annotations.SerializedName

data class TodoListRequestDto(
    @SerializedName("list")
    val list: List<TodoItemDto>
)