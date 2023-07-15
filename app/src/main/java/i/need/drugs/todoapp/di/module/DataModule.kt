package i.need.drugs.todoapp.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import i.need.drugs.todoapp.data.local.DatabaseApp
import i.need.drugs.todoapp.data.local.TodoDao
import i.need.drugs.todoapp.di.ApplicationScope
import i.need.drugs.todoapp.domain.Constant.DB_NAME

@Module
interface DataModule {
    companion object{
        @Provides
        @ApplicationScope
        fun provideDatabase(context: Context): DatabaseApp = Room.databaseBuilder(
            context,
            DatabaseApp::class.java,
            DB_NAME
        )
            .build()

        @Provides
        @ApplicationScope
        fun provideTaskDao(database: DatabaseApp): TodoDao = database.todoDao()
    }
}