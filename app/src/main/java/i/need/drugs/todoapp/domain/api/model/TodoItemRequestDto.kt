package i.need.drugs.todoapp.domain.api.model

import com.google.gson.annotations.SerializedName

data class TodoItemRequestDto(
    @SerializedName("element")
    val element: TodoItemDto
)