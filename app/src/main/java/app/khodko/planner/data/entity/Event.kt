package app.khodko.planner.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class Event(
    @ColumnInfo(name = "user_id") var userId: Long,
    @ColumnInfo(name = "tittle") var tittle: String,
    @ColumnInfo(name = "time") var time: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "month") var month: String,
    @ColumnInfo(name = "year") var year: String,
    @ColumnInfo(name = "start") val start: Long,
    @ColumnInfo(name = "ending") val ending: Long,
    @ColumnInfo(name = "repeat") val repeat: Int = 1,
    @ColumnInfo(name = "description") val description: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}
