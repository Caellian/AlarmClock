package hr.olfo.alarmclock.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import hr.olfo.alarmclock.R

import kotlinx.android.synthetic.main.activity_alarm_create.*

class AlarmCreate : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_create)
    }
}
