package app.khodko.planner.data.dao

import androidx.room.*
import app.khodko.planner.data.entity.Event


@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: Event): Long

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("SELECT * FROM event WHERE user_id = :userId")
    suspend fun getEvents(userId: Long): List<Event>

    @Query("SELECT * FROM event WHERE user_id = :userId AND date = :date")
    suspend fun getEventsByDate(userId: Long, date: String): List<Event>

    @Query("SELECT * FROM event WHERE user_id = :userId AND month = :month AND year = :year")
    suspend fun getEventsByMonthAndYear(userId: Long, month: String, year: String): List<Event>

    @Query("SELECT * FROM event WHERE id = :id")
    suspend fun getEvent(id: Long): List<Event>

}
