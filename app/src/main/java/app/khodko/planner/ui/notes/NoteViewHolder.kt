package app.khodko.planner.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.khodko.planner.R
import app.khodko.planner.core.stringToBitmap
import app.khodko.planner.data.entity.Note

class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val imageView: ImageView = view.findViewById(R.id.noteImage)
    private val tittleView: TextView = view.findViewById(R.id.tittleView)
    private val textView: TextView = view.findViewById(R.id.textView)

    fun bind(note: Note) {
        tittleView.text = note.tittle
        textView.text = note.text

        if (note.icon.isNotEmpty()) {
            imageView.setImageBitmap(stringToBitmap(note.icon))
        } else {
            imageView.setImageResource(R.drawable.ic_image_outline_24)
        }
    }

    companion object {
        fun create(parent: ViewGroup): NoteViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.note_view_item, parent, false)
            return NoteViewHolder(view)
        }
    }
}
