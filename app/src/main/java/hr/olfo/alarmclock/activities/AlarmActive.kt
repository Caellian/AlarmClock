package hr.olfo.alarmclock.activities

import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import hr.olfo.alarmclock.AlarmClock
import hr.olfo.alarmclock.R
import hr.olfo.alarmclock.data.Alarm
import hr.olfo.alarmclock.util.Constants
import kotlinx.android.synthetic.main.activity_alarm_active.*

class AlarmActive: AppCompatActivity(), SensorEventListener {

    lateinit var alarm: Alarm
    lateinit var ringtone: Ringtone

    val handler = Handler()

    lateinit var sm: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_active)

        val preferences: SharedPreferences = applicationContext.getSharedPreferences(Constants.PreferencesAlarms, Context.MODE_PRIVATE)

        val args: Bundle? = intent.extras
        val id = args?.getString(Constants.AlarmID, "") ?: ""
        if (id.isBlank()) {
            finish()
        } else {
            alarm = AlarmClock.gson.fromJson<Alarm>(preferences.getString(id, ""), Alarm::class.java)
        }

        labelAlarmName.text = alarm.name

        if (alarm.snoozeTime == 0) buttonSnooze.visibility = View.GONE
        buttonSnooze.setOnClickListener {
            snooze()
        }

        buttonOff.setOnClickListener {
            ringtone.stop()
            finish()
        }

        val uri = Uri.parse(alarm.ringtoneUri)
        ringtone = RingtoneManager.getRingtone(this, uri)

        ringtone.play()

        if (alarm.snoozeOnMove) {
            sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val accelerometer = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

            sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    fun snooze() {
        buttonSnooze.isEnabled = false
        ringtone.stop()

        handler.postDelayed({
            buttonSnooze.isEnabled = true
            ringtone.play()
        }, alarm.snoozeTime * 60000L)
    }

    override fun onStop() {
        super.onStop()

        if (alarm.snoozeOnMove) sm.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val diff = Math.sqrt((x*x + y*y + z*z).toDouble())
            if (diff > 0.5) {
                snooze()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}