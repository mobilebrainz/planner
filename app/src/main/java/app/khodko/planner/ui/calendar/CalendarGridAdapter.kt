package app.khodko.planner.ui.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import app.khodko.planner.R
import app.khodko.planner.core.date.DateFormat
import app.khodko.planner.data.entity.Event
import java.util.*


class CalendarGridAdapter(
    context: Context,
    private val dates: List<Date?>,
    private val currentDate: Calendar,
    private val events: List<Event>
) : ArrayAdapter<Any?>(
    context,
    R.layout.calendar_cell_layout
) {

    private var currDate: String = DateFormat.dateFormat.format(
        Calendar.getInstance(Locale.ENGLISH).time
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val monthDate: Date? = dates[position]
        val dateCalendar: Calendar = Calendar.getInstance()
        dateCalendar.time = monthDate!!
        val day = dateCalendar.get(Calendar.DAY_OF_MONTH)
        val month = dateCalendar.get(Calendar.MONTH) + 1
        val year = dateCalendar.get(Calendar.YEAR)
        val currentYear = currentDate.get(Calendar.YEAR)
        val currentMonth = currentDate.get(Calendar.MONTH) + 1

        val view: View
        if (convertView == null) {
            view = LayoutInflater.from(context)
                .inflate(R.layout.calendar_cell_layout, parent, false)

            val cellNumber: TextView = view.findViewById(R.id.calendar_day)
            cellNumber.text = day.toString()

            var textColor = R.color.calendarMonthDayTextColor
            val backguoundColor = when {
                currDate == DateFormat.dateFormat.format(monthDate) -> {
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
            view.setBackgroundColor(view.context.getColor(backguoundColor))
            cellNumber.setTextColor(view.context.getColor(textColor))

            val eventText: TextView = view.findViewById(R.id.event_id)
            showEvents(eventText, monthDate)
        } else {
            view = convertView
        }
        return view
    }

    private fun showEvents(eventText: TextView, calendarDate: Date) {
        val dayEvents = mutableListOf<Event>()
        for (event in events) {
            val eventDate = Date(event.start)
            if (DateFormat.equalDatesByDay(calendarDate, eventDate)) {
                dayEvents.add(event)
                eventText.text = dayEvents.size.toString() + " events"
            }
        }
    }

    override fun getCount(): Int {
        return dates.size
    }

    override fun getItem(position: Int): Any? {
        return dates[position]
    }

    override fun getPosition(item: Any?): Int {
        return dates.indexOf(item)
    }

}