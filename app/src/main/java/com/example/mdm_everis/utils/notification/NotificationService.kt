package com.example.mdm_everis.utils.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.example.mdm_everis.MainActivity
import com.example.mdm_everis.R
import java.util.*

class NotificationService : IntentService("NotificationService"){


    private lateinit var mNotification : Notification
    private val mNotificationId = 1000

    private fun createChannel(name : String, importance: Int, description : String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val context = this.applicationContext
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId = "${context.packageName}-name"
            val channel = NotificationChannel(channelId,name,importance)
            with(channel){
                enableVibration(true)
                setShowBadge(true)
                enableLights(true)
                lightColor = R.color.colorPrimary
                this.description = description
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        createChannel(getString(R.string.app_name),
            NotificationManagerCompat.IMPORTANCE_DEFAULT,
            "App notification channel.")

        var timestamp : Long = 0
        intent?.extras?.let {
            timestamp = it.getLong("timestamp")
        }
        if (timestamp > 0){
            val context = this.applicationContext
            val channelId = "${context.packageName}-name"
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notifyIntent = Intent(this,MainActivity::class.java)
            val title = "Reserva"
            val msg = "Tienes una reserva que finaliza hoy"
            with(notifyIntent){
                putExtra("title", title)
                putExtra("message",msg)
                putExtra("notification",true)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timestamp
            val pendingIntent = PendingIntent.getActivity(context,0,
                notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT)
            val res = this.resources
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                mNotification = Notification.Builder(this,channelId)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_favorite_red)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setStyle(Notification.BigTextStyle().bigText(msg))
                    .setContentText(msg)
                    .build()
            }else{
                mNotification = Notification.Builder(this)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_favorite_red)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setStyle(Notification.BigTextStyle().bigText(msg))
                    .build()
            }
            notificationManager.notify(mNotificationId,mNotification)
        }
    }

}