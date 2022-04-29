package app.khodko.planner.ui.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.khodko.planner.R
import app.khodko.planner.core.date.DateFormat
import app.khodko.planner.data.entity.Event
import java.util.*


class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val cellView: LinearLayout = view.findViewById(R.id.cell_view)
    private val dayView: TextView = view.findViewById(R.id.calendar_day)
    private val eventsView: TextView = view.findViewById(R.id.event_id)
    private lateinit var date: Date

    fun bind(date: Date, monthCalendar: Calendar, events: List<Event>) {
        this.date = date

        showDay(monthCalendar)
        showEvents(events)
    }

    private fun showDay(monthCalendar: Calendar) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        val currentYear = monthCalendar.get(Calendar.YEAR)
        val currentMonth = monthCalendar.get(Calendar.MONTH) + 1

        var textColor = R.color.calendarMonthDayTextColor

        val currentDate = DateFormat.dateFormat.format(Calendar.getInstance(Locale.ENGLISH).time)
        val backgroundColor = when {
            currentDate == DateFormat.dateFormat.format(date) -> {
                R.color.calendarCurrentDayColor
            }
            month == currentMonth && year == currentYear -> {
                R.color.calendarMonthDayColor
            }
            else -> {
                textColor = R.color.calendarAnotherDayTextColor
                R.color.calendarAnotherDayColor
            }
        }
        itemView.setBackgroundColor(itemView.context.getColor(backgroundColor))

        dayView.text = day.toString()
        dayView.setTextColor(itemView.context.getColor(textColor))

    }

    private fun showEvents(events: List<Event>) {
        val dayEvents = mutableListOf<Event>()
        for (event in events) {
            val eventDate = Date(event.start)
            if (DateFormat.isDate(event.repeat, eventDate, date)) {
                dayEvents.add(event)
                eventsView.text = dayEvents.size.toString()
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): CalendarViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.calendar_item, parent, false)
            return CalendarViewHolder(view)
        }
    }

}