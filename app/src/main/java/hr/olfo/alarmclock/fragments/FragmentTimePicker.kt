package hr.olfo.alarmclock.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hr.olfo.alarmclock.R

import kotlinx.android.synthetic.main.fragment_time_picker.*

class FragmentTimePicker: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.id.fragmentTimePicker, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}