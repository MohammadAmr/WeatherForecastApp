package com.example.weatherforecastapp.alerts.views

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.example.weatherforecastapp.R
import java.util.*

class AlertsFragment : Fragment() {

    //Text Views
    private lateinit var txtDateTo : TextView
    private lateinit var txtDateFrom : TextView

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
                txtDateFrom.text = mDay.toString() + "/" + mMonth + "/" + mYear}, year, month, day)
            dpd.show()
        }

        txtDateTo.setOnClickListener{
            val dpd = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                txtDateTo.text = mDay.toString() + "/" + mMonth + "/" + mYear}, year, month, day)
            dpd.show()
        }

    }

}