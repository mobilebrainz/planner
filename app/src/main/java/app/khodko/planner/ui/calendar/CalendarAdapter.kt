package app.khodko.planner.ui.calendar

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.khodko.planner.core.date.DateFormat
import app.khodko.planner.data.entity.Event
import java.util.*


class CalendarAdapter(
    private val dates: List<Date>,
    private val monthCalendar: Calendar,
    private val events: List<Event>,
    private val clickDate: Date?
) : RecyclerView.Adapter<CalendarViewHolder>() {

    var onClickListener: ((date: Date) -> Unit)? = null

    fun getPosition(date: Date): Int? {
        for (index in dates.indices) {
            if (DateFormat.equalDatesByDay(date, dates[index])) {
                return index
            }
        }
        return null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = dates[position]
        onClickListener?.apply {
            holder.itemView.setOnClickListener { invoke(date) }
        }
        holder.bind(date, monthCalendar, events, clickDate)
    }

    override fun getItemCount() = dates.size

}

