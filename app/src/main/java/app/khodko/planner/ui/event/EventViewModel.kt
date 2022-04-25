package app.khodko.planner.ui.event

import androidx.lifecycle.ViewModel
import app.khodko.planner.data.repository.EventRepository

class EventViewModel(
    private val eventRepository: EventRepository,
    val id: Long
) : ViewModel() {

}