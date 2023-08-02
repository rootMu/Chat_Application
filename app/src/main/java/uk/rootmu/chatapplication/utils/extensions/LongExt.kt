package uk.rootmu.chatapplication.utils.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Long.toDayOfWeek(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this

    // Create a SimpleDateFormat to format the day of the week
    val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())

    // Return the formatted day of the week
    return dayOfWeekFormat.format(calendar.time)
}

fun Long.toTime(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this

    // Create a SimpleDateFormat to format the time in HH:mm format
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(calendar.time)
}