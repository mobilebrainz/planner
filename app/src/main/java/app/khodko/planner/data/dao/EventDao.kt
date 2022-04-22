package app.khodko.planner.data.dao

import androidx.room.*
import app.khodko.planner.data.entity.Event


@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: Event): Long

    @Delete
    suspend fun delete(event: Event)

    @Query("SELECT * FROM event WHERE user_id = :userId")
    suspend fun getEvents(userId: Long): List<Event>

    @Query("SELECT * FROM event WHERE id = :id")
    suspend fun getEvent(id: Long): List<Event>

}
