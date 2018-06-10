package hr.olfo.alarmclock

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import hr.olfo.alarmclock.util.Constants

class AlarmService : Service() {

    val mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder {
        when (intent?.action) {
            Constants.ActionInit -> {

            }
        }

        return AlarmBinder(this)
    }

    class AlarmBinder(val service: AlarmService) : Binder() {

    }

    interface ServiceListener {
        fun onServiceConnected(serviceBinder: AlarmBinder)
    }
}