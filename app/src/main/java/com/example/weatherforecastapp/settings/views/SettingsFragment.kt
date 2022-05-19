package com.example.weatherforecastapp.settings.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.weatherforecastapp.R

class SettingsFragment : Fragment() {

    private lateinit var done: Button
    private lateinit var units : RadioGroup
    private lateinit var rMetric : RadioButton
    private lateinit var rImperial : RadioButton
    private lateinit var rDefault : RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        initUI(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    private fun initUI(view: View)
    {
        done = view.findViewById(R.id.donee)
        units = view.findViewById(R.id.unitsGroupp)

        rMetric = view.findViewById(R.id.rMetricc)
        rImperial = view.findViewById(R.id.rImperiall)
        rDefault = view.findViewById(R.id.rDefaultt)


        var str = "metric"
        val sharedPref = activity?.getSharedPreferences("shared", Context.MODE_PRIVATE)
        str = sharedPref?.getString("units", "metrics") ?: "metrics"
        when(str){
            "default" -> units.check(R.id.rDefaultt)
            "imperial" -> units.check(R.id.rImperiall)
            "metrics" -> units.check(R.id.rMetricc)
        }


        done.setOnClickListener(View.OnClickListener {

            when (units.checkedRadioButtonId) {
                R.id.rMetricc -> {
                    str = "metric"
                    Log.i("m3lsh", "Condition: Metrics")
                }
                R.id.rImperiall ->{
                    str = "imperial"
                    Log.i("m3lsh", "Condition: Imperial")
                }
                R.id.rDefaultt ->{
                    str = "default"
                    Log.i("m3lsh", "Condition: Default")
                }
            }
            Log.i("M3lsh", "Settings screen: units = ${str}")
            val sharedPref = activity?.getSharedPreferences("shared", Context.MODE_PRIVATE)
            val editor = sharedPref?.edit()
            editor?.apply{
                putString("units", str)
                apply()
            }
            val tmpStr = sharedPref?.getString("units", "m3lsh")
            Toast.makeText(requireActivity(), "Unit = ${tmpStr}", Toast.LENGTH_SHORT).show()
        })
    }
}