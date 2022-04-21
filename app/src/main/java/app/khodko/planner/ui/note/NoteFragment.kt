package app.khodko.planner.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.khodko.planner.App
import app.khodko.planner.core.BaseFragment
import app.khodko.planner.core.extension.getViewModelExt
import app.khodko.planner.databinding.FragmentNoteBinding

class NoteFragment : BaseFragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        noteViewModel = getViewModelExt {
            NoteViewModel(
                noteRepository = App.instance.noteRepository
            )
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}