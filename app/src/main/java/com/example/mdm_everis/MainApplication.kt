package com.example.mdm_everis

import android.app.Application
import com.example.data.KoinData
import com.example.domain.KoinDomain
import org.koin.android.ext.android.startKoin

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(this,
            listOf(KoinApp().appModule,
                KoinDomain().domainModule,
                KoinData().dataModule))
    }
}