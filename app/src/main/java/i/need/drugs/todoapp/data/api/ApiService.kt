package i.need.drugs.todoapp.data.api

import i.need.drugs.todoapp.domain.api.model.TodoItemRequestDto
import i.need.drugs.todoapp.domain.api.model.TodoListRequestDto
import i.need.drugs.todoapp.domain.api.model.TodoItemResponseDto
import i.need.drugs.todoapp.domain.api.model.TodoListResponseDto
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface ApiService {
    @GET("list")
    suspend fun downloadTodoList(): Response<TodoListResponseDto>

    @PATCH("list")
    suspend fun updateTodoList(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body body: TodoListRequestDto
    ): Response<TodoListResponseDto>

    @GET("list/{id}")
    suspend fun downloadTodoItem(
        @Path("id") id: UUID
    ): Response<TodoItemResponseDto>

    @POST("list")
    suspend fun loadTodoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body body: TodoItemRequestDto
    ): Response<TodoItemResponseDto>

    @PUT("list/{id}")
    suspend fun editTodoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: UUID,
        @Body body: TodoItemRequestDto
    ): Response<TodoItemResponseDto>

    @DELETE("list/{id}")
    suspend fun deleteTodoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: UUID
    ): Response<TodoItemResponseDto>
}