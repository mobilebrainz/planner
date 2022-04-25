package app.khodko.planner.ui.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.khodko.planner.R
import app.khodko.planner.data.entity.Event


class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tittleView: TextView = view.findViewById(R.id.tittleView)
    private val startView: TextView = view.findViewById(R.id.startView)

    fun bind(event: Event) {
        tittleView.text = event.tittle
        startView.text = event.date
    }

    companion object {
        fun create(parent: ViewGroup): EventViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.event_view_item, parent, false)
            return EventViewHolder(view)
        }
    }

}