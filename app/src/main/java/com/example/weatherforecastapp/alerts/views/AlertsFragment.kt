package com.example.weatherforecastapp.alerts.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.model.myWorker
import com.example.weatherforecastapp.utility.Helper
import java.util.*
import java.util.concurrent.TimeUnit

class AlertsFragment : Fragment() {

    //Text Views
    private lateinit var txtDateTo : TextView
    private lateinit var txtDateFrom : TextView
    private lateinit var txtTimee : TextView

    //Buttons
    private lateinit var btnAdd : Button

    //Radio Groups
    private lateinit var notifyGroup : RadioGroup

    //Radio Buttons
    private lateinit var radAlarm : RadioButton
    private lateinit var radNotification : RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_alerts, container, false)
        initUI(view)
        return view
    }

    private fun initUI(view : View) {
        //TextViews
        txtDateFrom = view.findViewById(R.id.txtDateFrom)
        txtDateTo   = view.findViewById(R.id.txtDateTo)
        txtTimee    = view.findViewById(R.id.txtTimee)

        //Buttons
        btnAdd      = view.findViewById(R.id.btnAdd)

        //Radio Groups
        notifyGroup = view.findViewById(R.id.notifyGroup)

        //Radio Buttons
        radAlarm    = view.findViewById(R.id.radAlarm)
        radNotification = view.findViewById(R.id.radNotification)

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        txtDateFrom.setOnClickListener{
            val dpd = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                txtDateFrom.text = Helper.formatDate(mYear,mMonth,mDay)}, year, month, day)
            dpd.show()
        }

        txtDateTo.setOnClickListener{
            val dpd = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                txtDateTo.text = Helper.formatDate(mYear,mMonth,mDay) }, year, month, day)
            dpd.show()
        }

        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker = TimePickerDialog(requireActivity(), object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

                txtTimee.setText(Helper.formatTime(hourOfDay, minute))
            }
        }, hour, minute, true)

        txtTimee.setOnClickListener({ v ->
            mTimePicker.show()
        })


        btnAdd.setOnClickListener(View.OnClickListener {
            val d1 : String = txtDateFrom.text.toString() + " " + txtTimee.text.toString() + ":00"
            val d2 : String = Helper.dateFormat.format(Calendar.getInstance().time)
            val time = Helper.findDifference(d1, d2)

            Log.i("M3lsh", "d1= ${d1}\nd2= ${d2}")
            Toast.makeText(requireActivity(), "Time = ${time} seconds", Toast.LENGTH_SHORT).show()

            val data : Data = Data.Builder()
                .putString("endDate", txtDateTo.text.toString() + " " + txtTimee.text.toString() + ":00")
                .putString("time", txtTimee.text.toString())
                .putBoolean("isOk", false)
                .build()



            val myWorkRequest : WorkRequest = OneTimeWorkRequestBuilder<myWorker>()
                .setInitialDelay(time, TimeUnit.SECONDS)
                .setInputData(data)
                .build()

            WorkManager.getInstance(requireActivity().applicationContext)
                .enqueue(myWorkRequest)
        })
    }

}