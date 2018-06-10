package hr.olfo.alarmclock.data

import android.net.Uri
import hr.olfo.alarmclock.util.Day
import java.util.*

class Alarm {
    val id = UUID.randomUUID().toString()

    var name: String = ""

    // TODO: Replace with TimeRange
    var timeH: Int = 0
    var timeM: Int = 0

    var repeat = mutableMapOf<Day, Boolean>()

    var volume: Int = 100

    var ringtoneUri: String = ""
    var ringtoneName: String = "Default"

    var snoozeTime = 0
    var snoozeOnMove = false
}