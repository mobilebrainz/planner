package app.khodko.planner.core.date

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormat {

    val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    val dateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    val monthFormat: SimpleDateFormat = SimpleDateFormat("MMMM", Locale.ENGLISH)
    val yearFormat: SimpleDateFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)
    val timeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    val prettyDateFormat: SimpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
    val dateTimeFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH)

    fun rangeDate(start: Date, ending: Date) =
        dateTimeFormat.format(start) + " - " + DateFormat.timeFormat.format(ending)

    fun toDate(dateInString: String): Date? {
        var date: Date? = null
        try {
            date = dateFormat.parse(dateInString)
        } catch (e: ParseException) {
            //e.printStackTrace()
        }
        return date
    }

}

