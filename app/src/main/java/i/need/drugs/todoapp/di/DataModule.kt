package i.need.drugs.todoapp.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import i.need.drugs.todoapp.data.api.ApiBuilder
import i.need.drugs.todoapp.data.api.ApiRepositoryImpl
import i.need.drugs.todoapp.data.api.ApiService
import i.need.drugs.todoapp.data.db.DatabaseApp
import i.need.drugs.todoapp.data.db.TodoItemDao
import i.need.drugs.todoapp.data.db.TodoListRepositoryImpl
import i.need.drugs.todoapp.domain.api.ApiRepository
import i.need.drugs.todoapp.domain.db.TodoListRepository

@Module
interface DataModule {

    @Binds
    fun bindApiRepository(impl: ApiRepositoryImpl) : ApiRepository

    @Binds
    fun bindTodoListRepository(impl: TodoListRepositoryImpl) : TodoListRepository

    companion object{
        @Provides
        fun provideTodoItemDao(application: Application): TodoItemDao {
            return DatabaseApp.getInstance(application).todoItemDao()
        }
        @Provides
        fun provideApiService(): ApiService {
            return ApiBuilder().service()
        }
    }
}