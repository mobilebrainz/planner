package app.khodko.planner.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.khodko.planner.App
import app.khodko.planner.R
import app.khodko.planner.core.BaseFragment
import app.khodko.planner.core.date.DateFormat
import app.khodko.planner.core.extension.getViewModelExt
import app.khodko.planner.core.extension.navigateExt
import app.khodko.planner.databinding.FragmentCalendarBinding

class CalendarFragment : BaseFragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var calendarViewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        calendarViewModel = getViewModelExt {
            CalendarViewModel(
                eventRepository = App.instance.eventRepository,
                userId = checkUserId()
            )
        }

        initListeners()
        initObservers()

        calendarViewModel.loadEventsByMonth(calendarViewModel.clickDate)
        binding.dateView.text = DateFormat.prettyDateFormat.format(calendarViewModel.clickDate)

        return binding.root
    }

    override fun initFab() {
        fab.setImageResource(R.drawable.ic_add_24)
        fab.show()
        fab.setOnClickListener {
            navigateExt(CalendarFragmentDirections.actionNavCalendarToNavNewEvent())
        }
    }

    private fun initListeners() {
        binding.customCalendarView.onChangeMonth {
            calendarViewModel.loadEventsByMonth(binding.customCalendarView.calendar.time)
        }

        binding.customCalendarView.onClickListener { date ->
            calendarViewModel.clickDate = date
            binding.dateView.text = DateFormat.prettyDateFormat.format(date)
            calendarViewModel.loadEventsByDate(date)
        }
    }

    private fun initObservers() {
        calendarViewModel.monthEvents.observe(viewLifecycleOwner) { events ->
            binding.customCalendarView.setUpCalendar(events)
        }

        calendarViewModel.dayEvents.observe(viewLifecycleOwner) { events ->
            // update event recycler with day events
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}