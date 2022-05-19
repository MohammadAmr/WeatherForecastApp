package com.example.weatherforecastapp.dialog

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.example.weatherforecastapp.R

class AlertDialog (private val context: Context, private val description: String)
{
    private lateinit var windowManager: WindowManager
    private lateinit var alertDialogView: View

    fun setMyWindowManger() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        alertDialogView = inflater.inflate(R.layout.alert_dialog, null)
        bindView()
        val LAYOUT_FLAG: Int
        LAYOUT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = (context.resources.displayMetrics.widthPixels * 0.85).toInt()
        val params = WindowManager.LayoutParams(
            width,
            WindowManager.LayoutParams.WRAP_CONTENT,
            LAYOUT_FLAG,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE,
            PixelFormat.TRANSLUCENT
        )
        windowManager!!.addView(alertDialogView, params)
    }

    private fun bindView() {
        val txtMsg = alertDialogView?.findViewById<TextView>(R.id.txtMsg)
        txtMsg?.text = description
        val imgClose = alertDialogView?.findViewById<ImageView>(R.id.imgClose)
        imgClose.setImageResource(R.drawable.ic_baseline_close_24)
        imgClose?.setOnClickListener {
            close()
        }
    }

    private fun close() {
        try {
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).removeView(
                alertDialogView
            )
            alertDialogView!!.invalidate()
            (alertDialogView!!.parent as ViewGroup).removeAllViews()
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }


    }



}