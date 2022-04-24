package app.khodko.planner.ui.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.GridView
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import app.khodko.planner.R
import app.khodko.planner.core.date.DateFormat
import app.khodko.planner.data.entity.Event
import java.util.*


private const val MAX_CALENDAR_DAYS = 42

class CustomCalendarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var previousButton: ImageButton
    private var nextButton: ImageButton
    private var gridView: GridView
    private var currentDate: TextView
    private lateinit var adapter: CalendarGridAdapter

    val calendar: Calendar = Calendar.getInstance(Locale.ENGLISH)
    private val dates = mutableListOf<Date>()

    private var clickListener: ((Date) -> Unit)? = null
    fun onClickListener(listener: (Date) -> Unit) {
        clickListener = listener
    }

    private var changeMonth: (() -> Unit)? = null
    fun onChangeMonth(listener: () -> Unit) {
        changeMonth = listener
    }

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.calendar_layout, this, true)
        previousButton = view.findViewById(R.id.previousBtn)
        nextButton = view.findViewById(R.id.nextBtn)
        gridView = view.findViewById(R.id.gridview)
        currentDate = view.findViewById(R.id.current_Date)
        initListeners()
    }

    private fun initListeners() {
        previousButton.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            changeMonth?.apply { invoke() }
        }
        nextButton.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            changeMonth?.apply { invoke() }
        }
        gridView.setOnItemClickListener { _, _, position, _ ->
            val date = dates[position]
            clickListener?.apply { invoke(date) }
        }
    }

    fun setUpCalendar(events: List<Event>) {
        val currDate = DateFormat.monthYearFormat.format(calendar.time)
        currentDate.text = currDate

        dates.clear()
        val monthCalendar = calendar.clone() as Calendar
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth)

        while (dates.size < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        adapter = CalendarGridAdapter(context, dates, calendar, events)
        gridView.adapter = adapter
    }

}