package i.need.drugs.todoapp.data.api

import android.app.Application
import android.content.Context
import android.util.Log
import i.need.drugs.todoapp.domain.api.ApiRepository
import i.need.drugs.todoapp.domain.db.TodoItem
import i.need.drugs.todoapp.presentation.setRevision
import java.util.*
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val context: Application,
    private val service: ApiService,
    private val mapper: ApiMapper
    ) : ApiRepository{

    override suspend fun downloadTodoList(): List<TodoItem>? {
        try {
            val response = service.downloadTodoList().body()
            if (response != null) {
                Log.d("-response", "${response.revision} ${response.status}")
                return if (response.status == "ok") {
                    context.setRevision(response.revision)
                    mapper.mapListDtoToListEntity(response.list)
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.d("error", e.message.toString())
        }
        return null
    }
    override suspend fun updateTodoList(revision: Int, body: List<TodoItem>): List<TodoItem>? {
        try {
            val raw = service.updateTodoList(revision, mapper.mapListEntityToListRequestDto(body))

            val response = raw.body()

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
            val raw = service.loadTodoItem(revision, mapper.mapEntityToItemRequestDto(body))
            Log.d("addTodoItem", raw.raw().toString())
            Log.d("addTodoItem_rev1", revision.toString())
            Log.d("addTodoItem_rev2", raw.body()?.revision.toString())
            val response = raw.body()
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
            val raw = service.deleteTodoItem(revision, id)
            Log.d("deleteTodoItem", "rev1: $revision")
            Log.d("deleteTodoItem", "raw: ${raw.raw().toString()}")
            val response = raw.body()
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