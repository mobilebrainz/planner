package app.khodko.planner.ui.newevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import app.khodko.planner.App
import app.khodko.planner.R
import app.khodko.planner.core.BaseFragment
import app.khodko.planner.core.date.DateFormat
import app.khodko.planner.core.extension.getViewModelExt
import app.khodko.planner.data.entity.Event
import app.khodko.planner.databinding.FragmentNewEventBinding
import app.khodko.planner.ui.addnote.AddNoteFragmentArgs
import java.util.*

class NewEventFragment : BaseFragment() {

    private var _binding: FragmentNewEventBinding? = null
    private val binding get() = _binding!!

    private lateinit var newEventViewModel: NewEventViewModel
    private var userId: Long = -1
    private var start = false
    private var startDate: Date = Calendar.getInstance().time

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewEventBinding.inflate(inflater, container, false)
        arguments?.let {
            val args = AddNoteFragmentArgs.fromBundle(it)
            val id = args.id
            userId = checkUserId()
            newEventViewModel = getViewModelExt {
                NewEventViewModel(
                    eventRepository = App.instance.eventRepository, id = id
                )
            }
            initObservers()
            initListeners()
        }
        binding.timePicker.setIs24HourView(true)
        return binding.root
    }

    override fun initFab() {
        fab.setImageResource(R.drawable.ic_done_24)
        fab.show()
        fab.setOnClickListener {
            saveEvent()
        }
    }

    private fun initListeners() {
        binding.startBtn.setOnClickListener {
            binding.datePickerView.isVisible = true
            fab.hide()
            start = true

            val calendar = Calendar.getInstance()
            calendar.time = startDate
            binding.datePicker.updateDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }

        binding.datePickerCancel.setOnClickListener {
            binding.datePickerView.isVisible = false
            fab.show()
            start = false
        }
        binding.datePickerOK.setOnClickListener {
            binding.datePickerView.isVisible = false
            binding.timePickerView.isVisible = true
            val calendar = Calendar.getInstance()
            if (start) {
                calendar.time = startDate
                binding.timePicker.hour = calendar.get(Calendar.HOUR_OF_DAY)
                binding.timePicker.minute = calendar.get(Calendar.MINUTE)
            }
        }
        binding.timePickerCancel.setOnClickListener {
            binding.timePickerView.isVisible = false
            fab.show()
            start = false
        }
        binding.timePickerOK.setOnClickListener {
            binding.timePickerView.isVisible = false
            fab.show()

            val cal = Calendar.getInstance()
            cal.set(
                binding.datePicker.year,
                binding.datePicker.month,
                binding.datePicker.dayOfMonth,
                binding.timePicker.hour,
                binding.timePicker.minute
            )
            val date = cal.time
            if (start) startDate = date
            start = false
        }
    }

    private fun saveEvent() {
        val tittle = binding.editTittle.text.toString().trim()
        when {
            tittle.isEmpty() -> {
                binding.editTittle.requestFocus()
                binding.editTittle.error = getString(R.string.tittle_field_error)
            }
            else -> {

                val event = Event(
                    userId = userId,
                    tittle = tittle,
                    time = DateFormat.timeFormat.format(startDate),
                    date = DateFormat.dateFormat.format(startDate),
                    month = DateFormat.monthFormat.format(startDate),
                    year = DateFormat.yearFormat.format(startDate),
                    start = startDate.time,
                    repeat = 1
                )
                newEventViewModel.save(event)
            }
        }
    }

    private fun initObservers() {
        newEventViewModel.savedEvent.observe(viewLifecycleOwner) {
            showInfoSnackbar(R.string.event_saved)
        }
        newEventViewModel.event.observe(viewLifecycleOwner) { e ->
            binding.editTittle.setText(e.tittle)
            startDate = Date(e.start)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}