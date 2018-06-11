package hr.olfo.alarmclock

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Binder
import android.os.IBinder
import com.google.gson.Gson
import hr.olfo.alarmclock.activities.AlarmActive
import hr.olfo.alarmclock.data.Alarm
import hr.olfo.alarmclock.util.Constants
import java.util.*

class AlarmService : Service() {
    private lateinit var preferences: SharedPreferences

    val timer = Timer("AlarmClock")
    val alarms = mutableListOf<Alarm>()

    override fun onBind(intent: Intent): IBinder {
        preferences = applicationContext.getSharedPreferences(Constants.PreferencesAlarms, Context.MODE_PRIVATE)
        when (intent.action) {
            Constants.ActionInit -> {
                val alarmList = preferences.getStringSet(Constants.AlarmList, emptySet())
                alarms += alarmList.mapNotNull { gson.fromJson(preferences.getString(it, ""), Alarm::class.java) }

                timer.scheduleAtFixedRate(object: TimerTask() {
                    override fun run() {
                        val c = Calendar.getInstance()
                        val h = c.get(Calendar.HOUR_OF_DAY)
                        val m = c.get(Calendar.MINUTE)
                        val d = c.get(Calendar.DAY_OF_WEEK)

                        alarms.filter { it.enabled }.forEach {
                            if (it.timeH == h && it.timeM == m && (it.activeOnDay(d) || !it.repeat.values.contains(true))) {
                                startAlarm(it.id)

                                if (!it.repeat.values.contains(true)) {
                                    it.enabled = false
                                }
                            }
                        }
                    }
                }, 0, 60000)
            }
        }

        return AlarmBinder(this)
    }

    fun startAlarm(id: String) {
        val intent = Intent(this, AlarmActive::class.java)
        intent.putExtra(Constants.AlarmID, id)
        startActivity(intent)
    }

    class AlarmBinder(val service: AlarmService) : Binder() {
        fun addAlarm(alarm: Alarm) {
            service.alarms += alarm
        }
    }

    companion object {
        val gson = Gson()
    }
}