package hr.olfo.alarmclock.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import hr.olfo.alarmclock.R
import hr.olfo.alarmclock.data.Alarm
import hr.olfo.alarmclock.util.Constants
import hr.olfo.alarmclock.util.Util

import kotlinx.android.synthetic.main.activity_alarm_preview.*

class AlarmPreview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_preview)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val intent = Intent(this, AlarmCreate::class.java)
            startActivity(intent)
        }

        refreshAlarmList()
    }

    fun refreshAlarmList() {
        val preferences: SharedPreferences = applicationContext.getSharedPreferences(Constants.PreferencesAlarms, Context.MODE_PRIVATE)

        alarms.removeAllViewsInLayout()

        val alarmList = preferences.getStringSet(Constants.AlarmList, mutableSetOf())
        alarmList.forEach {
            val alarm = AlarmCreate.gson.fromJson<Alarm>(preferences.getString(it, ""), Alarm::class.java)
            val b = Button(this).also {
                it.text = "[${Util.getDisplayTime(this, alarm.timeH, alarm.timeM)}] ${alarm.name}"
            }
            b.setOnClickListener {
                val intent = Intent(this, AlarmCreate::class.java)
                intent.putExtra(Constants.ExtraAlarmID, alarm.id)
                startActivity(intent)
            }
            b.setOnLongClickListener {
                preferences.edit().also {
                    it.remove(alarm.id)
                    it.putStringSet(Constants.AlarmList, alarmList.filter{it != alarm.id}.toSet())
                }.apply()
                b.visibility = View.INVISIBLE
                true
            }
            alarms.addView(b)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshAlarmList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_alarm_preview, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
