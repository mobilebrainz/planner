package app.khodko.planner.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.khodko.planner.R
import app.khodko.planner.data.entity.Note

class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tittleView: TextView = view.findViewById(R.id.tittleView)
    private val textView: TextView = view.findViewById(R.id.textView)

    fun bind(note: Note) {
        tittleView.text = note.tittle
        textView.text = note.text
    }

    companion object {
        fun create(parent: ViewGroup): NoteViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.note_view_item, parent, false)
            return NoteViewHolder(view)
        }
    }
}
