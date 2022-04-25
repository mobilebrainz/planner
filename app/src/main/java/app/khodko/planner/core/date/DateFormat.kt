package app.khodko.planner.core.date

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormat {

    val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val monthFormat: SimpleDateFormat = SimpleDateFormat("MMMM", Locale.ENGLISH)
    val yearFormat: SimpleDateFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)
    val timeFormat: SimpleDateFormat = SimpleDateFormat("K:mm a", Locale.ENGLISH)
    val prettyDateFormat: SimpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
    val dateTimeFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH)

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

