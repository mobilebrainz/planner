package app.khodko.planner.ui.newevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.khodko.planner.App
import app.khodko.planner.R
import app.khodko.planner.core.BaseFragment
import app.khodko.planner.core.extension.getViewModelExt
import app.khodko.planner.data.entity.Note
import app.khodko.planner.databinding.FragmentNewEventBinding
import app.khodko.planner.ui.addnote.AddNoteFragmentArgs
import java.text.SimpleDateFormat
import java.util.*

class NewEventFragment : BaseFragment() {

    private var _binding: FragmentNewEventBinding? = null
    private val binding get() = _binding!!

    private lateinit var newEventViewModel: NewEventViewModel
    private var userId: Long = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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
        }
        return binding.root
    }

    override fun initFab() {
        fab.setImageResource(R.drawable.ic_done_24)
        fab.show()
        fab.setOnClickListener {
            addEvent()
        }
    }

    private fun addEvent() {
        val tittle = binding.editTittle.text.toString().trim()
        when {
            tittle.isEmpty() -> {
                binding.editTittle.requestFocus()
                binding.editTittle.error = getString(R.string.tittle_field_error)
            }
            else -> {
                val allDays = binding.allDaySwitch.isChecked
                /*
                val sdf = SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.US)
                val currentDate = sdf.format(Calendar.getInstance().time)
                val note = Note(
                    userId = userId,
                    tittle = tittle,
                    text = text,
                    datetime = currentDate
                )
                newEventViewModel.save(note)
                 */
            }
        }
    }

    private fun initObservers() {
        newEventViewModel.savedEvent.observe(viewLifecycleOwner) {
            showInfoSnackbar(R.string.event_saved)
        }
        newEventViewModel.event.observe(viewLifecycleOwner) { e ->
            binding.editTittle.setText(e.tittle)
            binding.allDaySwitch.isChecked = e.allDay
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}