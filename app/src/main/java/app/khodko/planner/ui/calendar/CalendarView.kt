package app.khodko.planner.ui.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.khodko.planner.R
import app.khodko.planner.core.date.DateFormat
import app.khodko.planner.data.entity.Event
import java.util.*


private const val MAX_CALENDAR_DAYS = 42

class CalendarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var previousButton: ImageButton
    private var nextButton: ImageButton
    private var gridView: RecyclerView
    private var currentDate: TextView

    private lateinit var adapter: CalendarAdapter
    private val events = mutableListOf<Event>()
    val calendar: Calendar = Calendar.getInstance(Locale.ENGLISH)

    private var clickListener: ((date: Date, position: Int) -> Unit)? = null
    fun onClickListener(listener: (date: Date, position: Int) -> Unit) {
        clickListener = listener
    }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.calendar_view, this, true)
        previousButton = view.findViewById(R.id.previousBtn)
        nextButton = view.findViewById(R.id.nextBtn)
        gridView = view.findViewById(R.id.gridview)
        currentDate = view.findViewById(R.id.current_Date)
        initListeners()
    }

    fun setEvents(events: List<Event>) {
        this.events.clear()
        this.events.addAll(events)
        setUpCalendar()
    }

    private fun initListeners() {
        previousButton.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            setUpCalendar()
        }
        nextButton.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            setUpCalendar()
        }
    }

    private fun setUpCalendar() {
        currentDate.text = DateFormat.monthYearFormat.format(calendar.time)

        val monthCalendar = calendar.clone() as Calendar
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth)

        val dates = mutableListOf<Date>()
        while (dates.size < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        gridView.layoutManager = GridLayoutManager(context, 7)
        adapter = CalendarAdapter(dates, calendar, events)
        gridView.adapter = adapter

        adapter.onClickListener = { date, position ->
            clickListener?.apply { invoke(date, position) }
        }
    }

}