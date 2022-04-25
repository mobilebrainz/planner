package app.khodko.planner.ui.calendar

import android.content.Context
import android.graphics.Color
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
        val dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH)
        val displayMonth = dateCalendar.get(Calendar.MONTH) + 1
        val displayYear = dateCalendar.get(Calendar.YEAR)
        val currentYear = currentDate.get(Calendar.YEAR)
        val currentMonth = currentDate.get(Calendar.MONTH) + 1

        val view: View
        if (convertView == null) {
            view = LayoutInflater.from(context)
                .inflate(R.layout.calendar_cell_layout, parent, false)

            if (currDate == DateFormat.dateFormat.format(monthDate)) {
                view.setBackgroundColor(Color.parseColor("#9FA8DA"))
            } else if (displayMonth == currentMonth && displayYear == currentYear) {
                view.setBackgroundColor(Color.parseColor("#9CCC65"))
            } else {
                view.setBackgroundColor(Color.parseColor("#cccccc"))
            }
            val cellNumber: TextView = view.findViewById(R.id.calendar_day)
            cellNumber.text = dayNo.toString()

            val eventText: TextView = view.findViewById(R.id.event_id)
            val eventCalendar: Calendar = Calendar.getInstance()

            val dayEvents = mutableListOf<Event>()
            for (event in events) {
                DateFormat.toDate(event.date)?.let { date ->
                    eventCalendar.time = date
                    if (dayNo == eventCalendar.get(Calendar.DAY_OF_MONTH)
                        && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                        && displayYear == eventCalendar.get(Calendar.YEAR)
                    ) {
                        dayEvents.add(event)
                        eventText.text = dayEvents.size.toString() + " events"
                    }
                }
            }
        } else {
            view = convertView
        }
        return view
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