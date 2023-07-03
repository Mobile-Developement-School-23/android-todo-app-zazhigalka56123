package i.need.drugs.todoapp.data.api

import android.content.Context
import android.util.Log
import i.need.drugs.todoapp.domain.api.ApiRepository
import i.need.drugs.todoapp.domain.db.TodoItem
import i.need.drugs.todoapp.presentation.setRevision
import java.util.*

class ApiRepositoryImpl(private val context: Context) : ApiRepository{
    private val service = ApiBuilder().service()
    private val mapper = ApiMapper()

    override suspend fun downloadTodoList(): List<TodoItem>? {
        try {
            val response = service.downloadTodoList().body()
            if (response != null) {
                return if (response.status == "ok") {
                    context.setRevision(response.revision)
                    mapper.mapListDtoToListEntity(response.list)
                } else {
                    null
                }
            }
            return null
        } catch (e: Exception) {
            return null
        }
    }
    override suspend fun updateTodoList(revision: Int, body: List<TodoItem>): List<TodoItem>? {
        try {
            val response = service.updateTodoList(revision, mapper.mapListEntityToListRequestDto(body)).body()

            return if (response != null){
                if (response.status == "ok") {
                    context.setRevision(response.revision)
                    mapper.mapListDtoToListEntity(response.list)
                } else {
                    null
                }
            }else {
                null
            }

        }catch (e: Exception){
            return null
        }
    }

    override suspend fun downloadTodoItem(id: String): TodoItem? {
        try {
            val response = service.downloadTodoItem(UUID.fromString(id)).body()

            return if (response != null) {
                if (response.status == "ok") {
                    context.setRevision(response.revision)
                    mapper.mapDtoToEntity(response.element)
                } else {
                    null
                }
            }else {
                null
            }
        }catch (e: Exception) {
            return null
        }
    }

    override suspend fun loadTodoItem(revision: Int, body: TodoItem): TodoItem? {
        return try {
            val response = service.loadTodoItem(revision, mapper.mapEntityToItemRequestDto(body)).body()
            Log.d("addTodoItem", response.toString())
            if (response != null) {
                if (response.status == "ok") {
                    context.setRevision(response.revision)
                    mapper.mapDtoToEntity(response.element)
                } else {
                    null
                }
            }else {
                null
            }
        }catch (e: Exception) {
            Log.d("addTodoItem-error", e.message.toString())
            null
        }
    }

    override suspend fun editTodoItem(
        revision: Int,
        id: UUID,
        body: TodoItem
    ): TodoItem? {
        return try {
            val response = service.editTodoItem(revision, id, mapper.mapEntityToItemRequestDto(body)).body()
            if (response != null) {
                if (response.status == "ok") {
                    context.setRevision(response.revision)
                    mapper.mapDtoToEntity(response.element)
                } else {
                    null
                }
            }else {
                null
            }
        }catch (e: Exception) {
            Log.d("editTodoItem-error", e.message.toString())
            null
        }
    }

    override suspend fun deleteTodoItem(revision: Int, id: UUID): TodoItem? {
        return try {
            val response = service.deleteTodoItem(revision, id).body()
            Log.d("deleteTodoItem", service.deleteTodoItem(revision, id).raw().toString())

            if (response != null) {
                if (response.status == "ok") {
                    context.setRevision(response.revision)
                    mapper.mapDtoToEntity(response.element)
                } else {
                    null
                }
            }else {
                null
            }
        }catch (e: Exception) {
            Log.d("deleteTodoItem-error", e.message.toString())
            null
        }
    }

}