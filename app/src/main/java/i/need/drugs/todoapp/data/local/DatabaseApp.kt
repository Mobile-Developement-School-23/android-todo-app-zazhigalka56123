package i.need.drugs.todoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TodoDbModel::class],
    version = 1,
    exportSchema = false,
)
abstract class DatabaseApp : RoomDatabase(){

    abstract fun todoDao(): TodoDao
}