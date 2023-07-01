package i.need.drugs.todoapp.domain.api.model

import com.google.gson.annotations.SerializedName

data class TodoItemResponseDto(
    @SerializedName("status")
    val status: String,
    @SerializedName("element")
    val element: TodoItemDto,
    @SerializedName("revision")
    val revision: Int
)
