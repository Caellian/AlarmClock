package hr.olfo.alarmclock

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.google.gson.Gson
import hr.olfo.alarmclock.util.Constants

class AlarmClock : Application() {
    var serviceBinder: AlarmService.AlarmBinder? = null

    override fun onCreate() {
        super.onCreate()

        val si = Intent(this, AlarmService::class.java).apply {
            action = Constants.ActionInit
        }

        bindService(si, Connection(this), Context.BIND_ABOVE_CLIENT)
    }

    class Connection(val parent: AlarmClock) : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            parent.serviceBinder = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            parent.serviceBinder = service as? AlarmService.AlarmBinder
            parent.serviceBinder?.also { binder ->
                ServiceListeners.forEach { it.onServiceConnected(binder) }
            }
        }
    }

    companion object {
        val gson = Gson()

        val ServiceListeners = mutableListOf<AlarmService.ServiceListener>()
    }
}