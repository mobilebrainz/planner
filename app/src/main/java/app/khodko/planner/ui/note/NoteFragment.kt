package app.khodko.planner.ui.note

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import app.khodko.planner.App
import app.khodko.planner.R
import app.khodko.planner.core.BaseFragment
import app.khodko.planner.core.extension.getViewModelExt
import app.khodko.planner.core.extension.navigateExt
import app.khodko.planner.databinding.FragmentNoteBinding

class NoteFragment : BaseFragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        arguments?.let {
            val args = NoteFragmentArgs.fromBundle(it)
            val id = args.id
            noteViewModel = getViewModelExt {
                NoteViewModel(
                    noteRepository = App.instance.noteRepository,
                    id = id
                )
            }
            initObservers()
        }
        return binding.root
    }

    private fun initObservers() {
        noteViewModel.note.observe(viewLifecycleOwner) { n ->
            binding.tittleView.text = n.tittle
            binding.textView.text = n.text
            binding.dateView.text = n.datetime
        }
        noteViewModel.deletedNote.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.note_fragment_options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.edit -> {
                navigateExt(NoteFragmentDirections.actionNavNoteToNavAddNote(noteViewModel.id))
                true
            }
            R.id.delete -> {
                noteViewModel.delete()
                true
            }
            else -> false
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}