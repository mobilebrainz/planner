package app.khodko.planner.ui.event

import android.os.Bundle
import android.view.*
import app.khodko.planner.App
import app.khodko.planner.R
import app.khodko.planner.core.BaseFragment
import app.khodko.planner.core.extension.getViewModelExt
import app.khodko.planner.databinding.FragmentEventBinding

class EventFragment : BaseFragment() {

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventViewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        arguments?.let {
            val args = EventFragmentArgs.fromBundle(it)
            val id = args.id
            eventViewModel = getViewModelExt {
                EventViewModel(
                    eventRepository = App.instance.eventRepository,
                    id = id
                )
            }
            initObservers()
            //noteViewModel.load()
        }
        return binding.root
    }

    private fun initObservers() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.redact_options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.edit -> {
                //navigateExt(NoteFragmentDirections.actionNavEventToNavNewEvent(eventViewModel.id))
                true
            }
            R.id.delete -> {
                //eventViewModel.delete()
                true
            }
            else -> false
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}