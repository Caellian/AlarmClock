package hr.olfo.alarmclock.data

import java.util.*

class TimeRange {
    var beginTime: Date? = null
    var endTime: Date? = null

    val shifts = mutableListOf<Shift>()

    class Shift {
        val activeDays = mutableMapOf<Day, Time?>().apply {
            for (day in Day.values()) {
                this[day] = null
            }
        }
    }

    data class Time(val hours: Int, val minutes: Int = 0, val seconds: Int = 0)

    enum class Day {
        Monday,
        Tuesday,
        Wednesday,
        Thursday,
        Friday,
        Saturday,
        Sunday
    }
}