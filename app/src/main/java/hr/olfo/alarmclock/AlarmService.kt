package hr.olfo.alarmclock

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class AlarmService : Service() {

    val mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder {
        return AlarmBinder(this)
    }

    class AlarmBinder(val service: AlarmService) : Binder() {

    }

    interface ServiceListener {
        fun onServiceConnected(serviceBinder: AlarmBinder)
    }
}