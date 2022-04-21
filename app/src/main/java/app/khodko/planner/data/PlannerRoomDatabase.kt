package app.khodko.planner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.khodko.planner.data.dao.NoteDao
import app.khodko.planner.data.dao.UserDao
import app.khodko.planner.data.entity.Note
import app.khodko.planner.data.entity.User

@Database(entities = [User::class, Note::class], version = 1, exportSchema = false)
abstract class PlannerRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun noteDao(): NoteDao

    companion object {

        @Volatile
        private var INSTANCE: PlannerRoomDatabase? = null

        fun getDatabase(context: Context): PlannerRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        PlannerRoomDatabase::class.java,
                        "planner_database"
                    )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
