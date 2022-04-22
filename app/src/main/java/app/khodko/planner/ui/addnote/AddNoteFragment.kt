package app.khodko.planner.ui.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.khodko.planner.App
import app.khodko.planner.R
import app.khodko.planner.core.BaseFragment
import app.khodko.planner.core.bitmapToString
import app.khodko.planner.core.decodeUri
import app.khodko.planner.core.extension.getViewModelExt
import app.khodko.planner.core.stringToBitmap
import app.khodko.planner.data.entity.Note
import app.khodko.planner.databinding.FragmentAddNoteBinding
import app.khodko.planner.ui.activity.ImageChooserInterface
import java.text.SimpleDateFormat
import java.util.*

class AddNoteFragment : BaseFragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var addNoteViewModel: AddNoteViewModel
    private var userId: Long = -1
    private var icon = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        arguments?.let {
            val args = AddNoteFragmentArgs.fromBundle(it)
            val id = args.id
            userId = checkUserId()
            addNoteViewModel = getViewModelExt {
                AddNoteViewModel(noteRepository = App.instance.noteRepository, id = id)
            }
            initListeners()
            initObservers()
        }
        return binding.root
    }

    override fun initFab() {
        fab.setImageResource(R.drawable.ic_done_24)
        fab.show()
        fab.setOnClickListener {
            save()
        }
    }

    private fun initListeners() {
        binding.noteImage.setOnClickListener {
            val imageChooser = activity as ImageChooserInterface
            imageChooser.showImageChooser {
                it?.let {
                    binding.noteImage.setImageURI(it)
                    val btm = decodeUri(requireContext(), it, 500)
                    icon = bitmapToString(btm!!)
                }
            }
        }
    }

    private fun initObservers() {
        addNoteViewModel.savedNote.observe(viewLifecycleOwner) {
            showInfoSnackbar(R.string.note_saved)
        }
        addNoteViewModel.note.observe(viewLifecycleOwner) { n ->
            binding.editTittle.setText(n.tittle)
            binding.editNote.setText(n.text)
            if (n.icon.isNotEmpty()) {
                binding.noteImage.setImageBitmap(stringToBitmap(n.icon))
                icon = n.icon
            }
        }
    }

    private fun save() {
        val tittle = binding.editTittle.text.toString().trim()
        val text = binding.editNote.text.toString().trim()
        when {
            tittle.isEmpty() -> {
                binding.editTittle.requestFocus()
                binding.editTittle.error = getString(R.string.tittle_field_error)
            }
            text.isEmpty() -> {
                binding.editNote.requestFocus()
                binding.editNote.error = getString(R.string.note_field_error)
            }
            else -> {
                val sdf = SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.US)
                val currentDate = sdf.format(Calendar.getInstance().time)
                val note = Note(
                    userId = userId,
                    tittle = tittle,
                    text = text,
                    datetime = currentDate
                )
                note.icon = icon
                addNoteViewModel.save(note)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}