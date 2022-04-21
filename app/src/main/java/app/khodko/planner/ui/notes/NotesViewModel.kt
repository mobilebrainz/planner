package app.khodko.planner.ui.notes

import androidx.lifecycle.ViewModel
import app.khodko.planner.data.repository.NoteRepository

class NotesViewModel(
    private val noteRepository: NoteRepository,
    val userId: Long
) : ViewModel() {

    init {

    }
}