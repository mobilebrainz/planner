package app.khodko.planner

import android.app.Application
import app.khodko.planner.data.PlannerRoomDatabase
import app.khodko.planner.data.repository.UserRepository

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    val database by lazy { PlannerRoomDatabase.getDatabase(this) }
    val userRepository by lazy { UserRepository(database.userDao()) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}