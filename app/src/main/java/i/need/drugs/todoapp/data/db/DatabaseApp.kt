package i.need.drugs.todoapp.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TodoItemDbModel::class],
    version = 1,
    exportSchema = true,
)
abstract class DatabaseApp : RoomDatabase() {

    abstract fun todoItemDao(): TodoItemDao

    companion object {
        private var INSTANCE: DatabaseApp? = null
        private val LOCK = Any()
        private const val DB_NAME = "todo_item_db"

        fun getInstance(application: Application): DatabaseApp {
            INSTANCE?.let {
                return it
            }

            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }

                val db = Room.databaseBuilder(
                    application,
                    DatabaseApp::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = db

                return db
            }
        }
    }
}