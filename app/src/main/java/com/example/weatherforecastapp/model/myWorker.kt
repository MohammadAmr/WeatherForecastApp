package com.example.weatherforecastapp.model

import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.app.NotificationCompat.PRIORITY_DEFAULT
import androidx.core.content.ContextCompat.getSystemService
import com.example.weatherforecastapp.R
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.*
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.example.weatherforecastapp.dialog.AlertDialog
import com.example.weatherforecastapp.utility.Helper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class myWorker (appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {
    companion object{
        var cnt = 0
    }
    init {
        cnt = cnt + 1
    }
    override suspend fun doWork(): Result {

        val data : Data = inputData
        val endDate = data.getString("endDate")
        val time = data.getString("time")
        val isOk : Boolean = data.getBoolean("isOk", false)
        val notifyWithAlert : Boolean = data.getBoolean("alert", true)

        var msg = "Nothing to worry about :)"
        if (isOk) msg = "You need to take care ..."

        displayNotification(task = "Alert", desc = msg, imgID = R.drawable.weather)
        if (notifyWithAlert) {

            if (Settings.canDrawOverlays(applicationContext)) {
                GlobalScope.launch(Dispatchers.Main) {
                    val alertWindowManger = AlertDialog(applicationContext, msg)
                    alertWindowManger!!.setMyWindowManger()
                }
            }
        }

        val cal = Calendar.getInstance()
        cal.time = Date()
        cal.add(Calendar.DATE, 1)


        val tmpStr : String = Helper.dateFormat.format(cal.time)
        val nxtDate =  Helper.dateFormat.parse(tmpStr)
        Log.i("M3lsh", "nxtDate = ${tmpStr}\n endDate = ${endDate}" )

        val lastDate = Helper.dateFormat.parse(endDate)

        cal.time = Date()
        if (!nxtDate.after(lastDate)){
            Log.i("M3lsh", "The condition true" )
            val myWorkRequest : WorkRequest = OneTimeWorkRequestBuilder<myWorker>()
                .setInitialDelay(10, TimeUnit.MINUTES)
                .setInputData(inputData)
                .build()

            WorkManager.getInstance(applicationContext)
                .enqueue(myWorkRequest)
        }
        return Result.success()
    }
    private fun displayNotification(id:String = cnt.toString(), task:String,desc:String, imgID:Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel-1"
            val descriptionText = "channel-desc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(id, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        var builder = NotificationCompat.Builder(applicationContext, cnt.toString())
            .setSmallIcon(imgID)
            .setContentTitle(task)
            .setContentText(desc)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(applicationContext)) {
            // notificationId is a unique int for each notification that you must define
            notify(cnt.toInt(), builder.build())
        }
        Log.i("M3lsh", "displaying notification")
    }
}