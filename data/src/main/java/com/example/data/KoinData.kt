package com.example.data

import com.example.core.Network
import com.example.data.retrofit.DevicesRetrofit
import com.example.domain.DevicesRepository
import com.example.domain.login.LoginRepository
import org.koin.dsl.module.module

class KoinData {
    val dataModule by lazy {
        module{
            single<LoginRepository>{ LoginRepositoryImp()}
            single{Network.initRetrofit().create(DevicesRetrofit ::class.java)}
            single<DevicesRepository>{ DevicesRepositoryImp(get())}
        }
    }
}