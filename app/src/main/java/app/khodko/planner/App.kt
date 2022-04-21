package app.khodko.planner

import android.app.Application
import app.khodko.planner.data.PlannerRoomDatabase
import app.khodko.planner.data.repository.NoteRepository
import app.khodko.planner.data.repository.UserRepository

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    val database by lazy { PlannerRoomDatabase.getDatabase(this) }
    val userRepository by lazy { UserRepository(database.userDao()) }
    val noteRepository by lazy { NoteRepository(database.noteDao()) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}