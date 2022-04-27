package app.khodko.planner.ui.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.khodko.planner.R
import app.khodko.planner.core.date.DateFormat
import app.khodko.planner.data.entity.Event
import java.util.*


class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tittleView: TextView = view.findViewById(R.id.tittleView)
    private val startView: TextView = view.findViewById(R.id.startView)
    val editImage: ImageView = view.findViewById(R.id.editImage)
    val deleteImage: ImageView = view.findViewById(R.id.deleteImage)

    fun bind(event: Event) {
        tittleView.text = event.tittle
        startView.text = DateFormat.rangeTime(Date(event.start), Date(event.ending))
    }

    companion object {
        fun create(parent: ViewGroup): EventViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.event_view_item, parent, false)
            return EventViewHolder(view)
        }
    }

}