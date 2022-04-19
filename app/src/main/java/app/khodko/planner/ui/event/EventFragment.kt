package app.khodko.planner.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.khodko.planner.core.BaseFragment
import app.khodko.planner.core.extension.getViewModelExt
import app.khodko.planner.databinding.FragmentEventBinding

class EventFragment : BaseFragment() {

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventViewModel: EventViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        eventViewModel = getViewModelExt { EventViewModel() }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}