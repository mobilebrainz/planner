package app.khodko.planner.data.repository

import androidx.annotation.WorkerThread
import app.khodko.planner.data.dao.NoteDao
import app.khodko.planner.data.entity.Note

class NoteRepository(private val noteDao: NoteDao) {

    @WorkerThread
    suspend fun insert(note: Note) = noteDao.insert(note)

    @WorkerThread
    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    @WorkerThread
    suspend fun getNotes(userId: Long) = noteDao.getNotes(userId)

    @WorkerThread
    suspend fun getNote(id: Long) = noteDao.getNote(id)
}
