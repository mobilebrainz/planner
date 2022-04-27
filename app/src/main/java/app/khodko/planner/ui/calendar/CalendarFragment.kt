package app.khodko.planner.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import app.khodko.planner.App
import app.khodko.planner.R
import app.khodko.planner.core.BaseFragment
import app.khodko.planner.core.date.DateFormat
import app.khodko.planner.core.extension.getViewModelExt
import app.khodko.planner.core.extension.navigateExt
import app.khodko.planner.core.extension.showAlertDialogExt
import app.khodko.planner.data.entity.Event
import app.khodko.planner.databinding.FragmentCalendarBinding
import java.util.*

class CalendarFragment : BaseFragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var calendarViewModel: CalendarViewModel
    private var events: List<Event> = ArrayList()

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
        calendarViewModel.loadEvents()
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
        binding.customCalendarView.onClickListener { date ->
            calendarViewModel.clickDate = date
            binding.dateView.text = DateFormat.prettyDateFormat.format(date)
            initRecycler()
        }
    }

    private fun initObservers() {
        calendarViewModel.events.observe(viewLifecycleOwner) { events ->
            binding.customCalendarView.setEvents(events)
            this.events = events
            initRecycler()
        }

        calendarViewModel.deletedEvent.observe(viewLifecycleOwner) {
            calendarViewModel.loadEvents()
        }
    }

    private fun initRecycler() {
        val dayEvents = mutableListOf<Event>()
        for (event in events) {
            val eventDate = Date(event.start)
            if (DateFormat.equalDatesByDay(eventDate, calendarViewModel.clickDate)) {
                dayEvents.add(event)
            }
        }
        if (dayEvents.isEmpty()) {
            binding.eventsView.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
        } else {
            binding.eventsView.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
        }
        initRecycler(dayEvents)
    }

    private fun initRecycler(events: List<Event>) {
        val recyclerView = binding.eventsView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.isNestedScrollingEnabled = true

        val adapter = EventsAdapter()
        recyclerView.adapter = adapter
        adapter.submitList(events)

        addDividers()
        adapter.shotClickListener = { event, _ ->
            navigateExt(CalendarFragmentDirections.actionNavCalendarToNavEvent(event.id))
        }
        adapter.editClickListener = { event ->
            navigateExt(CalendarFragmentDirections.actionNavCalendarToNavNewEvent(event.id))
        }
        adapter.deleteClickListener = { event ->
            showAlertDialogExt(R.string.dialog_delete) {
                calendarViewModel.delete(event)
            }
        }
    }

    private fun addDividers() {
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.eventsView.addItemDecoration(decoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}