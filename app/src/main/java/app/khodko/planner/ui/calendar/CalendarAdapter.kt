package app.khodko.planner.ui.calendar

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.khodko.planner.data.entity.Event
import java.util.*


class CalendarAdapter(
    private val dates: List<Date>,
    private val monthCalendar: Calendar,
    private val events: List<Event>
) : RecyclerView.Adapter<CalendarViewHolder>() {

    var onClickListener: ((date: Date, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = dates[position]
        onClickListener?.apply {
            holder.itemView.setOnClickListener { invoke(date, position) }
        }
        holder.bind(date, monthCalendar, events)
    }

    override fun getItemCount() = dates.size

}

