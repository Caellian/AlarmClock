package hr.olfo.alarmclock.data

import hr.olfo.alarmclock.util.Day
import java.util.*
import kotlin.collections.HashMap

class Alarm {
    val id = UUID.randomUUID().toString()

    var name: String = ""
    var enabled: Boolean = true

    // TODO: Replace with TimeRange
    var timeH: Int = 0
    var timeM: Int = 0

    var repeat = mutableMapOf<Day, Boolean>()

    var volume: Int = 100

    var ringtoneUri: String = ""
    var ringtoneName: String = "Default"

    var snoozeTime = 0
    var snoozeOnMove = false

    fun clone(): Alarm {
        val result = Alarm()
        result.name = name
        result.enabled = enabled

        result.timeH = timeH
        result.timeM = timeM

        result.repeat = HashMap(repeat)
        result.volume = volume

        result.ringtoneUri = ringtoneUri
        result.ringtoneName = ringtoneName

        result.snoozeTime = snoozeTime
        result.snoozeOnMove = snoozeOnMove
        return result
    }
}