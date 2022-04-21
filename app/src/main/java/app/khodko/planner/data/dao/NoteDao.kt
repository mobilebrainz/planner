package app.khodko.planner.data.dao

import androidx.room.*
import app.khodko.planner.data.entity.Note


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note): Long

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM note WHERE user_id = :userId")
    suspend fun getNotes(userId: Long): List<Note>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNote(id: Long): List<Note>

}
