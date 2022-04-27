package app.khodko.planner.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.khodko.planner.core.viewmodel.SingleLiveEvent
import app.khodko.planner.data.entity.Note
import app.khodko.planner.data.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteRepository: NoteRepository,
    val id: Long
) : ViewModel() {

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note> = _note

    private val _deletedNote = SingleLiveEvent<Boolean>()
    val deletedNote: LiveData<Boolean> = _deletedNote

    fun load() {
        if (id > 0) {
            viewModelScope.launch {
                val notes = noteRepository.getNote(id)
                if (notes.isNotEmpty()) {
                    _note.value = notes[0]
                }
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            _note.value?.let {
                noteRepository.delete(it)
                _deletedNote.value = true
            }
        }
    }

}