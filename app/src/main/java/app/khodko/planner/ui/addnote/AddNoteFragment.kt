package app.khodko.planner.ui.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.khodko.planner.App
import app.khodko.planner.core.BaseFragment
import app.khodko.planner.core.extension.getViewModelExt
import app.khodko.planner.databinding.FragmentAddNoteBinding

class AddNoteFragment : BaseFragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var addNoteViewModel: AddNoteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        arguments?.let {
            val args = AddNoteFragmentArgs.fromBundle(it)
            val id = args.id

            addNoteViewModel = getViewModelExt {
                AddNoteViewModel(
                    noteRepository = App.instance.noteRepository,
                    userId = checkUserId(),
                    id = id
                )
            }
            initObservers()
            initListeners()
        }
        return binding.root
    }

    private fun initObservers() {

    }

    private fun initListeners() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}