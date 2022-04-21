package app.khodko.planner.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.khodko.planner.App
import app.khodko.planner.core.BaseFragment
import app.khodko.planner.core.extension.getViewModelExt
import app.khodko.planner.core.extension.navigateExt
import app.khodko.planner.databinding.FragmentNotesBinding

class NotesFragment : BaseFragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NotesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        notesViewModel = getViewModelExt {
            NotesViewModel(
                noteRepository = App.instance.noteRepository,
                userId = checkUserId()
            )
        }

        initObservers()
        initListeners()

        return binding.root
    }

    private fun initObservers() {

    }

    private fun initListeners() {
        binding.btnAddNote.setOnClickListener {
            navigateExt(NotesFragmentDirections.actionNavNotesToNavAddNote())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}