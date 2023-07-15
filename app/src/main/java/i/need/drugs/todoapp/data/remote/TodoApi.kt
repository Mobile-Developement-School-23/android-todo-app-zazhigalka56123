package i.need.drugs.todoapp.data.remote

import i.need.drugs.todoapp.data.remote.model.TodoListRequest
import i.need.drugs.todoapp.data.remote.model.TodoListResponse
import i.need.drugs.todoapp.data.remote.model.TodoRequest
import i.need.drugs.todoapp.data.remote.model.TodoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface TodoApi {
    @GET("list")
    suspend fun downloadTodoList(): Response<TodoListResponse>

    @PATCH("list")
    suspend fun updateTodoList(
        @Body body: TodoListRequest
    ): Response<TodoListResponse>

    @POST("list")
    suspend fun addTodo(
        @Body body: TodoRequest
    ): Response<TodoResponse>

    @PUT("list/{id}")
    suspend fun editTodo(
        @Path("id") id: UUID,
        @Body body: TodoRequest
    ): Response<TodoResponse>

    @DELETE("list/{id}")
    suspend fun deleteTodo(
        @Path("id") id: UUID
    ): Response<TodoResponse>
}