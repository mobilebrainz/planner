package app.khodko.planner.data.repository

import androidx.annotation.WorkerThread
import app.khodko.planner.data.dao.EventDao
import app.khodko.planner.data.entity.Event

class EventRepository(private val eventDao: EventDao) {

    @WorkerThread
    suspend fun insert(event: Event) = eventDao.insert(event)

    @WorkerThread
    suspend fun update(event: Event) {
        eventDao.update(event)
    }

    @WorkerThread
    suspend fun delete(event: Event) {
        eventDao.delete(event)
    }

    @WorkerThread
    suspend fun getEvents(userId: Long) = eventDao.getEvents(userId)

    @WorkerThread
    suspend fun getEvent(id: Long) = eventDao.getEvent(id)
}
