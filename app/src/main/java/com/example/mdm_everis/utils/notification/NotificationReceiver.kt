package com.example.mdm_everis.utils.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        val service = Intent(context,NotificationService::class.java)
        intent?.let {
            service.putExtra("reason" , it.getStringExtra("reason"))
            service.putExtra("timestamp",it.getLongExtra("timestamp",0))
        }
        context?.startService(service)
    }

}