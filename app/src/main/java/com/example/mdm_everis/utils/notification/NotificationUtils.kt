package com.example.mdm_everis.utils.notification

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import java.util.*

class NotificationUtils {

    fun setNotification(timeInMilliSeconds: Long, activity: Activity){
        if (timeInMilliSeconds > 0){
            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            val calendar = Calendar.getInstance()
            val pendingIntent : PendingIntent
            val alarmIntent = Intent(activity.applicationContext,NotificationReceiver::class.java)
            alarmIntent.putExtra("reason", "notification")
            alarmIntent.putExtra("timestamp", timeInMilliSeconds)
            calendar.timeInMillis = timeInMilliSeconds
            pendingIntent = PendingIntent.getBroadcast(activity,0,alarmIntent,PendingIntent.FLAG_CANCEL_CURRENT)
            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pendingIntent)
        }
    }

}