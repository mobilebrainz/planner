package app.khodko.planner.ui.addnote

import androidx.lifecycle.ViewModel
import app.khodko.planner.data.repository.NoteRepository

class AddNoteViewModel(
    private val noteRepository: NoteRepository,
    val userId: Long,
    private val id: Long
) : ViewModel() {

}