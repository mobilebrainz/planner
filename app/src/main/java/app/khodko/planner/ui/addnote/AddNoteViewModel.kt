package app.khodko.planner.ui.addnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.khodko.planner.core.viewmodel.SingleLiveEvent
import app.khodko.planner.data.entity.Note
import app.khodko.planner.data.repository.NoteRepository
import kotlinx.coroutines.launch

class AddNoteViewModel(
    private val noteRepository: NoteRepository,
    private var id: Long
) : ViewModel() {

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note> = _note

    private val _savedNote = SingleLiveEvent<Boolean>()
    val savedNote: LiveData<Boolean> = _savedNote

    init {
        if (id > 0) {
            viewModelScope.launch {
                val notes = noteRepository.getNote(id)
                if (notes.isNotEmpty()) {
                    _note.value = notes[0]
                }
            }
        }
    }

    fun save(note: Note) {
        viewModelScope.launch {
            if (id > 0) {
                note.id = id
            }
            val newId = noteRepository.insert(note)
            id = newId
            _note.value = note
            _savedNote.value = true
        }
    }

}