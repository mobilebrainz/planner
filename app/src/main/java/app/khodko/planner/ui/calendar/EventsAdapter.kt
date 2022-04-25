package app.khodko.planner.ui.calendar

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import app.khodko.planner.data.entity.Event


class EventsAdapter : ListAdapter<Event, EventViewHolder>(REPO_COMPARATOR) {

    var shotClickListener: ((Event, v: View) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            shotClickListener?.apply {
                holder.itemView.setOnClickListener { invoke(item, it) }
            }
            holder.bind(item)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean =
                oldItem == newItem
        }
    }
}
