package app.khodko.planner.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.khodko.planner.core.date.DateFormat
import app.khodko.planner.core.viewmodel.SingleLiveEvent
import app.khodko.planner.data.entity.Event
import app.khodko.planner.data.repository.EventRepository
import kotlinx.coroutines.launch
import java.util.*


class CalendarViewModel(
    private val eventRepository: EventRepository,
    val userId: Long
) : ViewModel() {

    var clickDate: Date = Calendar.getInstance(Locale.ENGLISH).time

    private val _monthEvents = MutableLiveData<List<Event>>()
    val monthEvents: LiveData<List<Event>> = _monthEvents

    private val _dayEvents = MutableLiveData<List<Event>>()
    val dayEvents: LiveData<List<Event>> = _dayEvents

    private val _deletedEvent = SingleLiveEvent<Boolean>()
    val deletedEvent: LiveData<Boolean> = _deletedEvent

    fun loadEventsByMonth(date: Date) {
        viewModelScope.launch {
            _monthEvents.value = eventRepository.getEventsByMonthAndYear(
                userId = userId,
                month = DateFormat.monthFormat.format(date),
                year = DateFormat.yearFormat.format(date)
            )
        }
    }

    fun loadEventsByDate(date: Date) {
        viewModelScope.launch {
            _dayEvents.value = eventRepository.getEventsByDate(
                userId = userId,
                date = DateFormat.dateFormat.format(date)
            )
        }
    }

    fun delete(event: Event) {
        viewModelScope.launch {
            eventRepository.delete(event)
            _deletedEvent.value = true
        }
    }

}