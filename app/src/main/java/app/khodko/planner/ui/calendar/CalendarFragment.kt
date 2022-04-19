package app.khodko.planner.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.khodko.planner.core.BaseFragment
import app.khodko.planner.core.extension.getViewModelExt
import app.khodko.planner.databinding.FragmentCalendarBinding

class CalendarFragment : BaseFragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var calendarViewModel: CalendarViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        calendarViewModel = getViewModelExt { CalendarViewModel() }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}